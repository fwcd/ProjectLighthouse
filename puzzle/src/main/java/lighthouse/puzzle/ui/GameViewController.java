package lighthouse.puzzle.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.BoardViewController;
import lighthouse.puzzle.ui.board.viewmodel.BoardStatistics;
import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.modes.PlayingMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.tickers.GameWinChecker;
import lighthouse.ui.AppContext;
import lighthouse.ui.ViewController;
import lighthouse.ui.tickers.TickerList;
import lighthouse.util.Flag;
import lighthouse.util.ListenerList;
import lighthouse.util.Updatable;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

/**
 * Manages the game board view, the current
 * perspective and the active game mode.
 */
public class GameViewController implements ViewController {
	private final JComponent component;
	
	private final AppContext context;
	private final PuzzleGameState model;
	private final DoubleVecBijection gridToPixels = new Scaling(70, 70);
	private final BoardViewController board;

	private GameMode mode;
	private GamePerspective perspective;
	
	private final List<Updatable> externalUpdaters = new ArrayList<>();
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;

	private final ListenerList<GamePerspective> perspectiveListeners = new ListenerList<>("GameViewController.perspectiveListeners");
	
	/** Creates a new game view controller using a given model. */
	public GameViewController(PuzzleGameState model, AppContext context) {
		this.model = model;
		this.context = context;
		
		component = new JPanel(new BorderLayout());

		// Initialize board
		board = new BoardViewController(model.getBoard(), model.getLevel().getBlockedStates(), gridToPixels, this::update);
		component.add(board.getComponent(), BorderLayout.CENTER);

		// Setup tickers
		winChecker = new GameWinChecker(component, board.getAnimationRunner(), model, context, board.getViewModel().getStatistics());

		// Add hooks
		Flag updatingBoard = new Flag(false);
		
		board.getViewModel().getBoardListeners().add(boardModel -> {
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
				board.getViewModel().getStatistics().reset();
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
		board.render();
		tickers.tick();
		
		for (Updatable updater : externalUpdaters) {
			updater.update();
		}
		
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
			// Otherwise delegate 'reset()'-call to board
			board.reset();
		}
		context.setStatus(mode.getBaseStatus());
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
		context.setStatus(mode.getBaseStatus());

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
		board.getViewModel().setBlockedStates(model.getLevel().getBlockedStates());
		board.setResponder(mode.createController(perspective, board.getViewModel(), this::update, board.getAnimationRunner()));
	}
	
	/** Fetche sthe currently active mode such as "editing" or "playing". */
	public GameMode getMode() { return mode; }
	
	/** Fetches the currently active perspective such as "start" or "in-game". */
	public GamePerspective getPerspective() { return perspective; }
	
	public ListenerList<GamePerspective> getPerspectiveListeners() { return perspectiveListeners; }
	
	public BoardViewController getBoard() { return board; }
	
	public PuzzleGameState getModel() { return model; }
	
	public AppContext getContext() { return context; }
	
	public TickerList getTickers() { return tickers; }
	
	public List<Updatable> getExternalUpdaters() { return externalUpdaters; }
	
	@Override
	public JComponent getComponent() { return component; }
}
