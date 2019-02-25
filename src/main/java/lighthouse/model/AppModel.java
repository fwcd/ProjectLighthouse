package lighthouse.model;

import lighthouse.util.LhConstants;

/**
 * The application's model.
 */
public class AppModel {
	/** Use the lighthouse's grid dimensions. */
	private final Grid grid = new Grid(LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	
	public Grid getGrid() {
		return grid;
	}
}
