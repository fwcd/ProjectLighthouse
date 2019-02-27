package lighthouse.ui.perspectives;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.ui.board.controller.BoardResponder;

/**
 * A game perspective determines WHICH board is
 * presented to the user.
 */
public interface GamePerspective extends Comparable<GamePerspective> {
	String getName();
	
	int getIndex();
	
	Board getActiveBoard(GameState model);
	
	BoardResponder createEditController(Board board);
	
	BoardResponder createPlayController(Board board);
	
	default boolean isInGame() { return false; }
	
	@Override
	default int compareTo(GamePerspective o) { return Integer.compare(getIndex(), o.getIndex()); }
}
