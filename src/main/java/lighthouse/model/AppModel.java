package lighthouse.model;

import lighthouse.util.LhConstants;

/**
 * The application's model.
 */
public class AppModel {
	/** Use the lighthouse's grid dimensions. */
	private final Board grid = new Board(LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	
	public Board getBoard() {
		return grid;
	}
}
