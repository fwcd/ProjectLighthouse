package lighthouse.model;

/**
 * The (entire) abstract lighthouse grid in all its glory.
 */
public class LighthouseGrid {
	private final int width;
	private final int height;
	/** The 4x6 game board. */
	private final GameBoard board = new GameBoard(4, 6);
	
	public LighthouseGrid(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
