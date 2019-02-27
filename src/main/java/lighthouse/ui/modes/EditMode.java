package lighthouse.ui.modes;

import lighthouse.model.Board;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class EditMode implements GameMode {
	public static final GameMode INSTANCE = new EditMode();
	
	private EditMode() {}
	
	@Override
	public Status getBaseStatus() { return new Status("Editing", ColorUtils.LIGHT_ORANGE); }
	
	@Override
	public BoardResponder createController(GamePerspective perspective, Board board) {
		return perspective.createEditController(board);
	}
}
