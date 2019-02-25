package lighthouse.model;

import lighthouse.util.LighthouseConstants;

/**
 * The application's model.
 */
public class AppModel {
	/** Use the lighthouse's grid dimensions. */
	private final Grid grid = new Grid(LighthouseConstants.LIGHTHOUSE_WIDTH, LighthouseConstants.LIGHTHOUSE_HEIGHT);
	
	public Grid getGrid() {
		return grid;
	}
}
