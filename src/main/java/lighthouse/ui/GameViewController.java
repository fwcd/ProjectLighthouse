package lighthouse.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.viewmodel.BoardStatistics;
import lighthouse.ui.discordrpc.DiscordRPCRunner;
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
	private final DiscordRPCRunner discordRPC = new DiscordRPCRunner();
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
		board = new BoardViewController(model.getBoard(), model.getLevel().getBlockedStates(), gridToPixels, this::update);
		component.add(board.getComponent(), BorderLayout.CENTER);

		// Setup tickers
		winChecker = new GameWinChecker(board, model, context, board.getViewModel().getStatistics());

		// Add hooks
		model.getBoardListeners().add(boardModel -> {
			board.updateModel(boardModel);
			board.getViewModel().getStatistics().reset();
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

		// Setup RPC
		discordRPC.setState(context.getStatus().getMessage());
		discordRPC.updatePresenceSoon();
		discordRPC.start();
		
		context.getStatusListeners().add(newStatus -> {
			discordRPC.setState(newStatus.getMessage());
			discordRPC.updatePresenceSoon();
		});
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
		
		discordRPC.setDetails(perspective.getName());
		discordRPC.updatePresenceSoon();
		
		perspectiveListeners.fire(perspective);
		update();
	}
	
	private void updateBoard() {
		Board activeBoard = perspective.getActiveBoard(model);
		board.updateModel(activeBoard);
		board.getViewModel().setBlockedStates(model.getLevel().getBlockedStates());
		board.setResponder(mode.createController(perspective, board.getViewModel(), this::update));
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
