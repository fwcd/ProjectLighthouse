package lighthouse.ui.modes;

import lighthouse.ui.board.BoardAnimationRunner;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.perspectives.InGamePerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;
import lighthouse.util.Updatable;

/**
 * The mode in which the player plays. Simple, right?
 */
public class PlayingMode implements GameMode {
	public static final GameMode INSTANCE = new PlayingMode();
	
	private PlayingMode() {}
	
	@Override
	public Status getBaseStatus() { return new Status("Playing", ColorUtils.LIGHT_GREEN); }
	
	@Override
	public GamePerspective getInitialPerspective() { return InGamePerspective.INSTANCE; }
	
	@Override
	public BoardResponder createController(GamePerspective perspective, BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner) {
		return perspective.createPlayController(board, gameUpdater, animationRunner);
	}
	
	@Override
	public boolean isPlaying() { return true; }
}
