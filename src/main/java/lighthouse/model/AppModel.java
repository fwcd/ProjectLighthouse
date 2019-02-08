package lighthouse.model;

/**
 * The application's model.
 */
public class AppModel {
	/** Use the lighthouse's grid dimensions. */
	private final Grid grid = new Grid(28, 14);
	
	public Grid getGrid() {
		return grid;
	}
}
