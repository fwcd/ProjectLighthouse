package lighthouse.ui;

import javax.swing.JComponent;

import lighthouse.model.Game;
import lighthouse.model.Status;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.controller.BoardDrawController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.util.ColorUtils;

/**
 * Manages the game board view and the current
 * game state.
 */
public class GameViewController implements ViewController {
	private final Game model;
	private final BoardViewController board;
	
	public GameViewController(Game model) {
		this.model = model;
		
		board = new BoardViewController(model.getState().getBoard());
		model.getState().getBoardListeners().add(board::updateModel);
		
		// Initially enter game mode
		newGame();
	}
	
	public void newGame() {
		model.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getState().getBoard()));
	}
	
	public void edit() {
		model.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		board.setResponder(new BoardDrawController(model.getState().getBoard()));
	}
	
	public void reset() {
		board.reset();
	}
	
	public BoardViewController getBoard() { return board; }
	
	@Override
	public JComponent getComponent() { return board.getComponent(); }
}
