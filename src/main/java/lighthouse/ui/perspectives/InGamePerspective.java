package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.NoResponder;

/**
 * Shows the in-game board of the level.
 */
public class InGamePerspective implements GamePerspective {
	public static final GamePerspective INSTANCE = new InGamePerspective();
	
	private InGamePerspective() {}
	
	@Override
	public String getName() { return "InGame"; }
	
	@Override
	public int getIndex() { return 1; }
	
	@Override
	public Board getActiveBoard(GameState model) { return model.getBoard(); }
	
	@Override
	public BoardResponder createEditController(Board board) { return NoResponder.INSTANCE; }
	
	@Override
	public BoardResponder createPlayController(Board board) { return new BoardPlayController(board); }
}
