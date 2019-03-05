package lighthouse.puzzle.ui.modes;

import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.util.Status;
import lighthouse.util.Updatable;

/**
 * A game mode such as "editing" or "playing".
 */
public interface GameMode {
	Status getBaseStatus();
	
	GamePerspective getInitialPerspective();
	
	BoardResponder createController(GamePerspective perspective, BoardViewModel board, Updatable gameUpdater, AnimationRunner animationRunner);
	
	default boolean isPlaying() { return false; }
}
