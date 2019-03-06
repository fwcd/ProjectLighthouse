package lighthouse.puzzle.ui.perspectives;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

/**
 * A game perspective determines WHICH board is
 * presented to the user.
 */
public interface GamePerspective extends Comparable<GamePerspective> {
	String getName();
	
	int getIndex();
	
	Board getActiveBoard(PuzzleGameState model);
	
	BoardResponder createEditController(BoardViewModel board, SceneInteractionFacade sceneFacade);
	
	BoardResponder createPlayController(BoardViewModel board, SceneInteractionFacade sceneFacade);
	
	default boolean isInGame() { return false; }
	
	@Override
	default int compareTo(GamePerspective o) { return Integer.compare(getIndex(), o.getIndex()); }
}
