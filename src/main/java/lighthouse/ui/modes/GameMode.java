package lighthouse.ui.modes;

import lighthouse.model.Board;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.perspectives.GamePerspective;
import lighthouse.ui.util.Status;

/**
 * A game mode such as "editing" or "playing".
 */
public interface GameMode {
	Status getBaseStatus();
	
	GamePerspective getInitialPerspective();
	
	BoardResponder createController(GamePerspective perspective, Board board);
	
	default boolean isPlaying() { return false; }
}
