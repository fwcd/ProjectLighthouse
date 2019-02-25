package lighthouse.model;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The game board model which usually represents
 * a subsection of the entire grid.
 */
public class Board {
	private final int columns;
	private final int rows;
	/** Associates ids with bricks. */
	private final Map<Integer, Brick> bricksById = new HashMap<>();
	
	public Board(int columns, int rows) {
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
	
	/** Fetches the cell's color at the specified position. */
	public Color getCell(int x, int y) {
		// TODO: Implement this
		return Color.BLACK;
	}
}
