package lighthouse.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
	
	/** Fetches the board's column count. */
	public int getWidth() {
		return width;
	}
	
	/** Fetches the board's row count. */
	public int getHeight() {
		return height;
	}
	
	/** Fetches all bricks on this board. */
	public Collection<Brick> getBricks() {
		return bricksById.values();
	}
	
	/** Fetches the brick at a specified position. */
	public Optional<Brick> getBrickAt(int x, int y) {
		throw new RuntimeException("TODO"); // TODO: Implement this method
	}
}
