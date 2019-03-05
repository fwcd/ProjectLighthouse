package lighthouse.ui.board.view;

import lighthouse.ui.board.viewmodel.BoardViewModel;

/**
 * The presentation component of the game board.
 */
public interface BoardView {
	void draw(BoardViewModel viewModel);
}
