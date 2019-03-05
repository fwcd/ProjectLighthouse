package lighthouse.puzzle.ui.perspectives;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.BoardAnimationRunner;
import lighthouse.puzzle.ui.board.controller.BoardDrawController;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.controller.NoResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.Updatable;

/**
 * Shows the starting board of the level.
 */
public class StartPerspective implements GamePerspective {
	public static final GamePerspective INSTANCE = new StartPerspective();
	
	private StartPerspective() {}
	
	@Override
	public String getName() { return "Start"; }
	
	@Override
	public int getIndex() { return 0; }
	
	@Override
	public Board getActiveBoard(PuzzleGameState model) { return model.getLevel().getStart(); }
	
	@Override
	public BoardResponder createEditController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return new BoardDrawController(board, gameUpdater);
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return NoResponder.INSTANCE;
	}
}
