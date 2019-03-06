package lighthouse.puzzle.ui.modes;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.perspectives.InGamePerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

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
	public BoardResponder createController(GamePerspective perspective, BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return perspective.createPlayController(board, sceneFacade);
	}
	
	@Override
	public boolean isPlaying() { return true; }
}
