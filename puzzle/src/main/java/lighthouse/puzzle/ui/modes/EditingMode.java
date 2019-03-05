package lighthouse.puzzle.ui.modes;

import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.puzzle.ui.perspectives.StartPerspective;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;
import lighthouse.util.Updatable;

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
	public BoardResponder createController(GamePerspective perspective, BoardViewModel board, Updatable gameUpdater, AnimationRunner animationRunner) {
		return perspective.createEditController(board, gameUpdater, animationRunner);
	}
	
	@Override
	public boolean isPlaying() { return false; }
}
