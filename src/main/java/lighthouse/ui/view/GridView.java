package lighthouse.ui.view;

import lighthouse.model.Grid;

/**
 * The presentation component of the user
 * interface. Responsible for drawing a colored grid.
 */
public interface GridView {
	void draw(Grid model);
}
