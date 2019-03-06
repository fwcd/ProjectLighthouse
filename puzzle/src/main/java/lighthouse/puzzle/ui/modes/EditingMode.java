package lighthouse.puzzle.ui.modes;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.perspectives.StartPerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

/**
 * A mode that allows the user to edit the
 * current level.
 */
public class EditingMode implements GameMode {
	public static final GameMode INSTANCE = new EditingMode();
	
	private EditingMode() {}
	
	@Override
	public Status getBaseStatus() { return new Status("Editing", ColorUtils.LIGHT_ORANGE); }
	
	@Override
	public GamePerspective getInitialPerspective() { return StartPerspective.INSTANCE; }
	
	@Override
	public BoardResponder createController(GamePerspective perspective, BoardViewModel board, SceneInteractionFacade sceneFacade) {
		return perspective.createEditController(board, sceneFacade);
	}
	
	@Override
	public boolean isPlaying() { return false; }
}
