package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.model.LevelStages;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.ScaleTransform;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.perspectives.GamePerspective;
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
	private final CoordinateMapper coordinateMapper;
	private final BoardViewController board;
	
	private Status status;
	private GamePerspective perspective;
	
	private final TickerList tickers = new TickerList();
	private final GameWinChecker winChecker;
	
	private final ListenerList<Status> statusListeners = new ListenerList<>();
	
	public GameViewController(GameState model) {
		this.model = model;
		
		component = new JPanel(new BorderLayout());
		
		// Initialize board
		coordinateMapper = new ScaleTransform(70, 70);
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
		model.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getBoard(), board.getFloatingContext(), coordinateMapper));
		
		model.getLevelStageListeners().remove(editControlListener);
		model.getLevelStageListeners().add(playControlListener);
		playControlListener.on(model.getLevelStage());
		
		tickers.add(winChecker);
		winChecker.reset();
		
		model.switchToStage(LevelStages.IN_GAME);
		model.startLevel();
	}
	
	/** Switches to editing mode. */
	public void edit() {
		model.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		
		model.getLevelStageListeners().add(editControlListener);
		model.getLevelStageListeners().remove(playControlListener);
		tickers.remove(winChecker);
		editControlListener.on(model.getLevelStage());
		
		model.switchToStage(LevelStages.START);
	}
	
	public void reset() {
		if (model.getLevelStage().isInGame()) {
			// Reset to the starting board...
			model.startLevel();
		} else {
			// Otherwise delegate 'reset()'-call to board
			board.reset();
		}
	}
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) { this.status = status; }
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
	
	public void setPerspective(GamePerspective perspective) { this.perspective = perspective; }
	
	public BoardViewController getBoard() { return board; }
	
	public GameState getModel() { return model; }
	
	public TickerList getTickers() { return tickers; }
	
	@Override
	public JComponent getComponent() { return component; }
}
