package lighthouse.puzzle.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.gameapi.GameInitializationContext;
import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.LocalBoardViewController;
import lighthouse.puzzle.ui.board.viewmodel.BoardStatistics;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.modes.PlayingMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.tickers.GameWinChecker;
import lighthouse.puzzle.ui.tickers.TickerList;
import lighthouse.ui.ObservableStatus;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.Flag;
import lighthouse.util.ListenerList;
import lighthouse.util.Updatable;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * Manages the puzzle game board, the current
 * perspective and the active game mode.
 */
public class PuzzleGameViewController implements SwingViewController {
	private final JComponent component;
	private final ObservableStatus status;
	
	private final PuzzleGameState model;
	private final LocalBoardViewController board;
	private final SceneInteractionFacade sceneFacade;

	private GameMode mode;
	private GamePerspective perspective;
	
	private final List<Updatable> externalUpdaters = new ArrayList<>();
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;

	private final ListenerList<GamePerspective> perspectiveListeners = new ListenerList<>("GameViewController.perspectiveListeners");
	
	/** Creates a new game view controller using a given model. */
	public PuzzleGameViewController(PuzzleGameState model, GameInitializationContext context, DoubleVecBijection gridToPixels) {
		this.model = model;
		
		status = context.getStatus();
		sceneFacade = context.getInteractionFacade();
		
		// Initialize board
		component = new JPanel(new BorderLayout());
		board = new LocalBoardViewController(model.getBoard(), gridToPixels);
		component.add(board.getComponent(), BorderLayout.CENTER);

		// Setup tickers
		winChecker = new GameWinChecker(null, sceneFacade, model, context.getStatus(), board.getViewModel().getStatistics());

		// Add hooks
		Flag updatingBoard = new Flag(false);
		
		BoardViewModel boardViewModel = board.getViewModel();
		boardViewModel.getBoardListeners().add(boardModel -> {
			if (updatingBoard.isFalse()) {
				updatingBoard.set(true);
				if (perspective.isInGame()) {
					model.setBoard(boardModel);
				}
				updatingBoard.set(false);
			}
		});
		model.getBoardListeners().add(boardModel -> {
			if (updatingBoard.isFalse()) {
				updatingBoard.set(true);
				boardViewModel.transitionTo(boardModel);
				boardViewModel.getStatistics().reset();
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
		tickers.tick(mode, perspective);
		
		for (Updatable updater : externalUpdaters) {
			updater.update();
		}
		
		sceneFacade.update();
		
		BoardStatistics stats = board.getViewModel().getStatistics();
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
			sceneFacade.reset();
		}
		status.set(mode.getBaseStatus());
		update();
	}
	
	/** Enters a game mode such as "editing" or "playing". */
	public void enter(GameMode mode) {
		if (mode.isPlaying()) {
			winChecker.reset();
			model.startLevel();
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
		BoardViewModel viewModel = board.getViewModel();
		
		viewModel.transitionTo(activeBoard);
		viewModel.setBlockedStates(model.getLevel().getBlockedStates());
		
		SceneResponder controller = mode.createController(perspective, viewModel, sceneFacade);
		board.setResponder(controller);
		sceneFacade.setResponder(controller);
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
	
	public BoardViewModel getBoardViewModel() { return board.getViewModel(); }
	
	@Override
	public JComponent getComponent() { return component; }
}
