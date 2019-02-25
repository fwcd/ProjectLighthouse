package lighthouse.ui.board.view;

import lighthouse.model.Board;

/**
 * The presentation component of the user
 * interface. Responsible for drawing a colored grid.
 */
public interface BoardView {
	void draw(Board model);
}
