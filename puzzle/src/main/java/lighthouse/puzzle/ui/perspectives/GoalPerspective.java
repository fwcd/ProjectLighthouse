package lighthouse.puzzle.ui.perspectives;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.controller.BoardArrangeController;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.controller.NoBoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

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
	public Board getActiveBoard(PuzzleGameState model) { return model.getLevel().getGoal(); }
	
	@Override
	public BoardResponder createEditController(BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return new BoardArrangeController(board, sceneFacade);
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return NoBoardResponder.INSTANCE;
	}
}
