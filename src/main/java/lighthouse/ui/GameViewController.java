package lighthouse.ui;

import javax.swing.JComponent;

import lighthouse.model.Game;
import lighthouse.model.GameStage;
import lighthouse.model.Status;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.EditingControllerPicker;
import lighthouse.util.ColorUtils;
import lighthouse.util.Listener;

/**
 * Manages the game board view and the current
 * game state.
 */
public class GameViewController implements ViewController {
	private final Game model;
	private final BoardViewController board;
	private final Listener<GameStage> editControlListener;
	
	public GameViewController(Game model) {
		this.model = model;
		
		board = new BoardViewController(model.getState().getBoard());
		model.getState().getBoardListeners().add(board::updateModel);
		
		editControlListener = stage -> {
			board.setResponder(stage.accept(new EditingControllerPicker(model.getState().getBoard())));
		};
		
		model.getState().getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
		});
		
		// Initially enter game mode
		play();
	}
	
	/** Switches to playing mode. */
	public void play() {
		model.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getState().getBoard()));
		model.getStageListeners().remove(editControlListener);
	}
	
	/** Switches to editing mode. */
	public void edit() {
		model.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		model.getStageListeners().add(editControlListener);
		editControlListener.on(model.getCurrentStage());
	}
	
	public void reset() {
		board.reset();
	}
	
	public BoardViewController getBoard() { return board; }
	
	@Override
	public JComponent getComponent() { return board.getComponent(); }
}
