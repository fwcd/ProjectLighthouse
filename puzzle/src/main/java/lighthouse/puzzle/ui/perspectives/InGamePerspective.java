package lighthouse.puzzle.ui.perspectives;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.controller.BoardPlayController;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.controller.NoBoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

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
	public Board getActiveBoard(PuzzleGameState model) { return model.getBoard(); }
	
	@Override
	public boolean isInGame() { return true; }
	
	@Override
	public BoardResponder createEditController(BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return NoBoardResponder.INSTANCE;
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return new BoardPlayController(board, sceneFacade);
	}
}
