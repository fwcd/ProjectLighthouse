package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.ScaleTransform;
import lighthouse.ui.modes.GameMode;
import lighthouse.ui.modes.PlayingMode;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.tickers.GameWinChecker;
import lighthouse.ui.tickers.TickerList;
import lighthouse.util.ListenerList;

/**
 * Manages the game board view and the current game/level stage.
 */
public class GameViewController implements ViewController {
	private final JComponent component;
	
	private final GameState model;
	private final GameContext context = new GameContext();
	private final CoordinateMapper coordinateMapper = new ScaleTransform(70, 70);
	private final BoardViewController board;
	
	private GameMode mode;
	private GamePerspective perspective;
	
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;
	
	private final ListenerList<GamePerspective> perspectiveListeners = new ListenerList<>();
	
	public GameViewController(GameState model) {
		this.model = model;
		
		component = new JPanel(new BorderLayout());
		
		// Initialize board
		board = new BoardViewController(model.getBoard(), coordinateMapper);
		model.getBoardListeners().add(board::updateModel);
		component.add(board.getComponent(), BorderLayout.CENTER);
		
		// Setup tickers
		winChecker = new GameWinChecker(board.getComponent(), model, context);
		
		// Add level hooks
		model.getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
		});
		Level initialLevel = model.getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());
		
		// Enter playing mode
		enter(PlayingMode.INSTANCE);
	}
	
	public void reset() {
		if (perspective.isInGame()) {
			// Reset to the starting board...
			model.startLevel();
		} else {
			// Otherwise delegate 'reset()'-call to board
			board.reset();
		}
	}
	
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
	}
	
	public void show(GamePerspective perspective) {
		this.perspective = perspective;
		perspectiveListeners.fire(perspective);
	}
	
	public GamePerspective getPerspective() { return perspective; }
	
	public ListenerList<GamePerspective> getPerspectiveListeners() { return perspectiveListeners; }
	
	public BoardViewController getBoard() { return board; }
	
	public GameState getModel() { return model; }
	
	public GameContext getContext() { return context; }
	
	public TickerList getTickers() { return tickers; }
	
	@Override
	public JComponent getComponent() { return component; }
}
