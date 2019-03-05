package lighthouse.ui.modes;

import lighthouse.ui.board.BoardAnimationRunner;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.util.Status;
import lighthouse.util.Updatable;

/**
 * A game mode such as "editing" or "playing".
 */
public interface GameMode {
	Status getBaseStatus();
	
	GamePerspective getInitialPerspective();
	
	BoardResponder createController(GamePerspective perspective, BoardViewModel board, Updatable gameUpdater, BoardAnimationRunner animationRunner);
	
	default boolean isPlaying() { return false; }
}
