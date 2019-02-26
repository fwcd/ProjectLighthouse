package lighthouse.ui.board.view;

import java.awt.Color;

import lighthouse.model.Board;
import lighthouse.util.IntVec;
import lighthouse.util.LhConstants;

/**
 * A class that wraps the board preparing it for
 * the big screen (the Lighthouse) by scaling and
 * translating the grid positions.
 */
public class LighthouseGrid {
	private final int columns;
	private final int rows;
	private final Board board;
	
	public LighthouseGrid(Board board) {
		this(board, LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	}
	
	public LighthouseGrid(Board board, int columns, int rows) {
		this.board = board;
		this.columns = columns;
		this.rows = rows;
	}
	
	/** Fetches the Lighthouse grid's columns. */
	public int getColumns() {
		return columns;
	}
	
	/** Fetches the Lighthouse grid's rows. */
	public int getRows() {
		return rows;
	}
	
	public Color colorAt(IntVec lhGridPos) {
		return board.colorAt(lhGridPos.sub(4, 1).scale(0.2, 0.5).floor());
	}
	
	public Color colorAt(int x, int y) {
		return colorAt(new IntVec(x, y));
	}
}
