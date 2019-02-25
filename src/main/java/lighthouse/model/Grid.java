package lighthouse.model;

import java.awt.Color;

/**
 * The (entire) abstract lighthouse grid in all its glory.
 */
public class Grid {
	private final int columns;
	private final int rows;
	/** The 4x6 game board. */
	private final GameBoard board = new GameBoard(4, 6);
	
	// TODO: Listeners
	
	public Grid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}
	
	/** Fetches the grid's column count. */
	public int getColumns() {
		return columns;
	}
	
	/** Fetches the grid's row count. */
	public int getRows() {
		return rows;
	}
	
	public Color getCell(int x, int y) {
		// TODO: Currently a sample color is outputted instead of the
		//       actual grid content. Check whether coordinate lies
		//       in the board's bounds and return the appropriate board
		//       cells if so.
		return new Color((x * 255) / columns, (y * 255) / rows, (x * y * 255) / (columns * rows));
	}
}
