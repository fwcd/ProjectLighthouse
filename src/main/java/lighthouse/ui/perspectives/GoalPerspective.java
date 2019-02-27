package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.controller.BoardArrangeController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.NoResponder;

/**
 * Shows the goal board of the level.
 */
public class GoalPerspective implements GamePerspective {
	public static final StartPerspective INSTANCE = new StartPerspective();
	
	@Override
	public Board getActiveBoard(GameState model) { return model.getLevel().getGoal(); }
	
	@Override
	public BoardResponder createEditController(Board board) { return new BoardArrangeController(board); }
	
	@Override
	public BoardResponder createPlayController(Board board) { return NoResponder.INSTANCE; }
}
