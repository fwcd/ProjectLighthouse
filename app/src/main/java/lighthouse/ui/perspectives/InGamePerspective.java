package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.BoardAnimationRunner;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.NoResponder;
import lighthouse.ui.board.viewmodel.BoardViewModel;
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
	public Board getActiveBoard(GameState model) { return model.getBoard(); }
	
	@Override
	public boolean isInGame() { return true; }
	
	@Override
	public BoardResponder createEditController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return NoResponder.INSTANCE;
	}
	
	@Override
	public BoardResponder createPlayController(BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return new BoardPlayController(board, gameUpdater, animationRunner);
	}
}
