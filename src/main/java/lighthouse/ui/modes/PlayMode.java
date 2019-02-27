package lighthouse.ui.modes;

import lighthouse.model.Board;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

/**
 * The mode in which the player plays. Simple, right?
 */
public class PlayMode implements GameMode {
	public static final GameMode INSTANCE = new PlayMode();
	
	private PlayMode() {}
	
	@Override
	public Status getBaseStatus() { return new Status("Playing", ColorUtils.LIGHT_GREEN); }
	
	@Override
	public BoardResponder createController(GamePerspective perspective, Board board) {
		return perspective.createPlayController(board);
	}
}
