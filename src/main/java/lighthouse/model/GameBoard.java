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
	private final int columns;
	private final int rows;
	/** Associates ids with bricks. */
	private final Map<Integer, Brick> bricksById = new HashMap<>();
	
	public GameBoard(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}
	
	/** Fetches the board's column count. */
	public int getColumns() {
		return columns;
	}
	
	/** Fetches the board's row count. */
	public int getRows() {
		return rows;
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
