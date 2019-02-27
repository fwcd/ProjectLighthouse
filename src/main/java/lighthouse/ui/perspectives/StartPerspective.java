package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.controller.BoardDrawController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.NoResponder;

/**
 * Shows the starting board of the level.
 */
public class StartPerspective implements GamePerspective {
	public static final GoalPerspective INSTANCE = new GoalPerspective();
	
	@Override
	public Board getActiveBoard(GameState model) { return model.getLevel().getStart(); }
	
	@Override
	public BoardResponder createEditController(Board board) { return new BoardDrawController(board); }
	
	@Override
	public BoardResponder createPlayController(Board board) { return NoResponder.INSTANCE; }
}
