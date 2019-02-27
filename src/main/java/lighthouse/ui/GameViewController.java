package lighthouse.ui;

import javax.swing.JComponent;

import lighthouse.model.Game;
import lighthouse.model.GamePlayingState;
import lighthouse.model.Level;
import lighthouse.model.LevelStage;
import lighthouse.model.LevelStages;
import lighthouse.model.Status;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.EditingControllerPicker;
import lighthouse.ui.board.controller.PlayControllerPicker;
import lighthouse.ui.tickers.GameWinChecker;
import lighthouse.ui.tickers.TickerList;
import lighthouse.util.ColorUtils;
import lighthouse.util.Listener;

/**
 * Manages the game board view and the current game/level stage.
 */
public class GameViewController implements ViewController {
	private final Game model;
	private final BoardViewController board;
	
	private final TickerList tickers = new TickerList();
	
	private final GameWinChecker winChecker;
	private final Listener<LevelStage> playControlListener;
	private final Listener<LevelStage> editControlListener;
	
	public GameViewController(Game model) {
		this.model = model;
		
		// Initialize board
		board = new BoardViewController(model.getState().getActiveBoard());
		model.getState().getBoardListeners().add(board::updateModel);
		
		// Setup tickers
		winChecker = new GameWinChecker(board.getComponent(), model);
		
		// Setup controller pickers
		editControlListener = stage -> {
			board.setResponder(stage.accept(new EditingControllerPicker(model.getState().getActiveBoard())));
		};
		playControlListener = stage -> {
			board.setResponder(stage.accept(new PlayControllerPicker(model.getState().getActiveBoard())));
		};
		
		// Add level hooks
		model.getState().getLevelListeners().add(level -> {
			LevelStage stage = model.getLevelStage();
			level.getGoal().bindToUpdates(level.getStart());
			stage.transitionFrom(stage, model.getState());
		});
		Level initialLevel = model.getState().getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());
		
		// Enter playing mode
		play();
	}
	
	/** Switches to playing mode. */
	public void play() {
		model.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getState().getActiveBoard()));
		
		model.getLevelStageListeners().remove(editControlListener);
		model.getLevelStageListeners().add(playControlListener);
		playControlListener.on(model.getLevelStage());
		
		tickers.add(winChecker);
		winChecker.reset();
		
		GamePlayingState state = model.getState();
		model.switchToStage(LevelStages.IN_GAME);
		state.setBoard(state.getLevel().getStart().copy());
	}
	
	/** Switches to editing mode. */
	public void edit() {
		model.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		
		model.getLevelStageListeners().add(editControlListener);
		model.getLevelStageListeners().remove(playControlListener);
		tickers.remove(winChecker);
		editControlListener.on(model.getLevelStage());
	}
	
	public void reset() {
		board.reset();
	}
	
	public BoardViewController getBoard() { return board; }
	
	public Game getModel() { return model; }
	
	public TickerList getTickers() { return tickers; }
	
	@Override
	public JComponent getComponent() { return board.getComponent(); }
}
