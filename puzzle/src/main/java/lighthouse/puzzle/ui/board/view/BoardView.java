package lighthouse.puzzle.ui.board.view;

import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

/**
 * The presentation component of the game board.
 */
public interface BoardView {
	void draw(BoardViewModel viewModel);
}
