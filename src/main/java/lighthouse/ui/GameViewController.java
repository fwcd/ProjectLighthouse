package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.ScaleTransform;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.perspectives.InGamePerspective;
import lighthouse.ui.perspectives.StartPerspective;
import lighthouse.ui.tickers.GameWinChecker;
import lighthouse.ui.tickers.TickerList;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;
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
		
		
		// Setup tickers
		winChecker = new GameWinChecker(board.getComponent(), model);
		
		// Add level hooks
		model.getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
		});
		Level initialLevel = model.getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());
		
		// Enter playing mode
		play();
	}
	
	/** Switches to playing mode. */
	public void play() {
		context.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getBoard()));
		
		tickers.add(winChecker);
		winChecker.reset();
		
		show(InGamePerspective.INSTANCE);
		model.startLevel();
	}
	
	/** Switches to editing mode. */
	public void edit() {
		context.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		tickers.remove(winChecker);
		show(StartPerspective.INSTANCE);
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
	
	public void show(GamePerspective perspective) { this.perspective = perspective; }
	
	public GamePerspective getPerspective() { return perspective; }
	
	public ListenerList<GamePerspective> getPerspectiveListeners() { return perspectiveListeners; }
	
	public BoardViewController getBoard() { return board; }
	
	public GameState getModel() { return model; }
	
	public TickerList getTickers() { return tickers; }
	
	@Override
	public JComponent getComponent() { return component; }
}
