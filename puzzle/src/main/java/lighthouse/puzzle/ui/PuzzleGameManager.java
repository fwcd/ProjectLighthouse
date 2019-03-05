package lighthouse.puzzle.ui;

import java.util.ArrayList;
import java.util.List;

import lighthouse.gameapi.GameInitializationContext;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.viewmodel.BoardStatistics;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.modes.PlayingMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.tickers.GameWinChecker;
import lighthouse.ui.ObservableStatus;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.ui.tickers.TickerList;
import lighthouse.util.Flag;
import lighthouse.util.ListenerList;
import lighthouse.util.Updatable;

/**
 * Manages the game board view, the current
 * perspective and the active game mode.
 */
public class PuzzleGameManager {
	private final ObservableStatus status;
	
	private final PuzzleGameState model;
	private final BoardViewModel board;
	private final DelegateResponder sceneResponder;
	private final AnimationRunner animationRunner;

	private GameMode mode;
	private GamePerspective perspective;
	
	private final List<Updatable> externalUpdaters = new ArrayList<>();
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;

	private final ListenerList<GamePerspective> perspectiveListeners = new ListenerList<>("GameViewController.perspectiveListeners");
	
	/** Creates a new game view controller using a given model. */
	public PuzzleGameManager(PuzzleGameState model, GameInitializationContext context) {
		this.model = model;
		
		status = context.getStatus();
		sceneResponder = context.getSceneResponder();
		animationRunner = context.getAnimationRunner();
		
		// Initialize board
		board = new BoardViewModel(model.getBoard());

		// Setup tickers
		winChecker = new GameWinChecker(null, context.getAnimationRunner(), model, context.getStatus(), board.getStatistics());

		// Add hooks
		externalUpdaters.add(context.getUpdater());
		
		Flag updatingBoard = new Flag(false);
		
		board.getBoardListeners().add(boardModel -> {
			if (updatingBoard.isFalse()) {
				updatingBoard.set(true);
				model.setBoard(boardModel);
				updatingBoard.set(false);
			}
		});
		model.getBoardListeners().add(boardModel -> {
			if (updatingBoard.isFalse()) {
				updatingBoard.set(true);
				board.transitionTo(boardModel);
				board.getStatistics().reset();
				updatingBoard.set(false);
			}
		});
		model.getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
			updateBoard();
			update();
		});
		Level initialLevel = model.getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());
		
		// Enter playing mode
		enter(PlayingMode.INSTANCE);
	}
	
	private void update() {
		tickers.tick();
		
		for (Updatable updater : externalUpdaters) {
			updater.update();
		}
		
		BoardStatistics stats = board.getStatistics();
		stats.setAvgDistanceToGoal(model.getLevel().avgDistanceToGoal(model.getBoard()));
	}
	
	/** "Resets" the game in some way. */
	public void reset() {
		if (perspective.isInGame()) {
			// Reset to the starting board...
			model.startLevel();
			winChecker.reset();
		} else {
			// Otherwise delegate 'reset()'-call to controller
			sceneResponder.reset();
		}
		status.set(mode.getBaseStatus());
		update();
	}
	
	/** Enters a game mode such as "editing" or "playing". */
	public void enter(GameMode mode) {
		if (mode.isPlaying()) {
			tickers.add(winChecker);
			winChecker.reset();
			model.startLevel();
		} else {
			tickers.remove(winChecker);
		}
		
		this.mode = mode;
		status.set(mode.getBaseStatus());

		show(mode.getInitialPerspective());
		update();
	}
	
	/** Presents a perspective of the game to the user. */
	public void show(GamePerspective perspective) {
		this.perspective = perspective;
		updateBoard();
		
		perspectiveListeners.fire(perspective);
		update();
	}
	
	private void updateBoard() {
		Board activeBoard = perspective.getActiveBoard(model);
		board.transitionTo(activeBoard);
		board.setBlockedStates(model.getLevel().getBlockedStates());
		sceneResponder.setDelegate(mode.createController(perspective, board, this::update, animationRunner));
	}
	
	/** Fetche sthe currently active mode such as "editing" or "playing". */
	public GameMode getMode() { return mode; }
	
	/** Fetches the currently active perspective such as "start" or "in-game". */
	public GamePerspective getPerspective() { return perspective; }
	
	public ListenerList<GamePerspective> getPerspectiveListeners() { return perspectiveListeners; }
	
	public PuzzleGameState getModel() { return model; }
	
	public TickerList getTickers() { return tickers; }
	
	public List<Updatable> getExternalUpdaters() { return externalUpdaters; }
	
	public ObservableStatus getStatus() { return status; }
	
	public BoardViewModel getBoardViewModel() { return board; }
}
