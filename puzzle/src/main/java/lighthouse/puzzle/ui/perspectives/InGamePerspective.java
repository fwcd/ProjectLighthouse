package lighthouse.puzzle.ui.perspectives;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.BoardAnimationRunner;
import lighthouse.puzzle.ui.board.controller.BoardPlayController;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.controller.NoBoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.Updatable;

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
	public BoardResponder createEditController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return NoBoardResponder.INSTANCE;
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return new BoardPlayController(board, gameUpdater, animationRunner);
	}
}
