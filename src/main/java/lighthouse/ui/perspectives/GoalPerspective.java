package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.BoardAnimationRunner;
import lighthouse.ui.board.controller.BoardArrangeController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.NoResponder;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.Updatable;

/**
 * Shows the goal board of the level.
 */
public class GoalPerspective implements GamePerspective {
	public static final GamePerspective INSTANCE = new GoalPerspective();
	
	private GoalPerspective() {}
	
	@Override
	public String getName() { return "Goal"; }
	
	@Override
	public int getIndex() { return 2; }
	
	@Override
	public Board getActiveBoard(GameState model) { return model.getLevel().getGoal(); }
	
	@Override
	public BoardResponder createEditController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return new BoardArrangeController(board, gameUpdater);
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return NoResponder.INSTANCE;
	}
}
