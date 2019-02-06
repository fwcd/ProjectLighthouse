package lighthouse.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The game board model which usually represents
 * a subsection of the entire grid.
 */
public class GameBoard {
	private final int width;
	private final int height;
	/** Associates ids with bricks. */
	private final Map<Integer, Brick> bricksById = new HashMap<>();
	
	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Collection<Brick> getBricks() {
		return bricksById.values();
	}
}
