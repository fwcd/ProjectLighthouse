package lighthouse.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.model.GameStatistics;
import lighthouse.model.Level;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.modes.GameMode;
import lighthouse.ui.modes.PlayingMode;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.tickers.GameWinChecker;
import lighthouse.ui.tickers.TickerList;
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

	private final GameState model;
	private final GameContext context = new GameContext();
	private final DoubleVecBijection gridToPixels = new Scaling(70, 70);
	private final BoardViewController board;

	private GameMode mode;
	private GamePerspective perspective;
	
	private final List<Updatable> externalUpdaters = new ArrayList<>();
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;

	private final ListenerList<GamePerspective> perspectiveListeners = new ListenerList<>("GameViewController.perspectiveListeners");
	
	/** Creates a new game view controller using a given model. */
	public GameViewController(GameState model) {
		this.model = model;

		component = new JPanel(new BorderLayout());

		// Initialize board
		board = new BoardViewController(model.getBoard(), gridToPixels, this::update);
		component.add(board.getComponent(), BorderLayout.CENTER);

		// Setup tickers
		winChecker = new GameWinChecker(board.getComponent(), model, context);

		// Add hooks
		model.getBoardListeners().add(boardModel -> {
			board.updateModel(boardModel);
			context.getStatistics().reset();
		});
		model.getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
		});
		Level initialLevel = model.getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());

		// Enter playing mode
		enter(PlayingMode.INSTANCE);
		update();
	}
	
	private void update() {
		board.render();
		tickers.tick();
		
		for (Updatable updater : externalUpdaters) {
			updater.update();
		}
		
		GameStatistics stats = context.getStatistics();
		stats.incrementMoveCount();
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
	}
	
	/** Enters a game mode such as "editing" or "playing". */
	public void enter(GameMode mode) {
		this.mode = mode;
		context.setStatus(mode.getBaseStatus());

		if (mode.isPlaying()) {
			tickers.add(winChecker);
			winChecker.reset();
			model.startLevel();
		} else {
			tickers.remove(winChecker);
		}

		show(mode.getInitialPerspective());
		update();
	}
	
	/** Presents a perspective of the game to the user. */
	public void show(GamePerspective perspective) {
		this.perspective = perspective;
		
		Board activeBoard = perspective.getActiveBoard(model);
		board.updateModel(activeBoard);
		board.setResponder(mode.createController(perspective, board.getViewModel(), this::update));
		
		perspectiveListeners.fire(perspective);
		update();
	}
	
	/** Fetche sthe currently active mode such as "editing" or "playing". */
	public GameMode getMode() { return mode; }
	
	/** Fetches the currently active perspective such as "start" or "in-game". */
	public GamePerspective getPerspective() { return perspective; }
	
	public ListenerList<GamePerspective> getPerspectiveListeners() { return perspectiveListeners; }
	
	public BoardViewController getBoard() { return board; }
	
	public GameState getModel() { return model; }
	
	public GameContext getContext() { return context; }
	
	public TickerList getTickers() { return tickers; }
	
	public List<Updatable> getExternalUpdaters() { return externalUpdaters; }
	
	@Override
	public JComponent getComponent() { return component; }
}
