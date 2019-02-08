package lighthouse.ui.view;

import java.awt.Color;

/**
 * The presentation component of the user
 * interface. Responsible for drawing a colored grid.
 */
public interface GridView {
	void draw(Color[][] grid);
}
