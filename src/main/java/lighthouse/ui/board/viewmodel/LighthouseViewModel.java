package lighthouse.ui.board.viewmodel;

import java.awt.Color;

import lighthouse.model.grid.ArrayColorGrid;
import lighthouse.model.grid.ColorGrid;
import lighthouse.model.grid.WritableColorGrid;
import lighthouse.util.IntVec;
import lighthouse.util.LhConstants;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;
import lighthouse.util.transform.Translation;

/**
 * A class that wraps the board preparing it for
 * the big screen (the Lighthouse) by scaling and
 * translating the grid positions.
 */
public class LighthouseViewModel implements ColorGrid {
	private final int columns;
	private final int rows;
	private final BoardViewModel board;
	private final WritableColorGrid overlayGrid;
	private final DoubleVecBijection lighthouseToGrid = new Translation(-4, -1).andThen(new Scaling(0.2, 0.5));
	
	public LighthouseViewModel(BoardViewModel board) {
		this(board, LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	}
	
	public LighthouseViewModel(BoardViewModel board, int columns, int rows) {
		this.board = board;
		this.columns = columns;
		this.rows = rows;
		overlayGrid = new ArrayColorGrid(rows, columns);
	}
	
	/** Fetches the Lighthouse grid's columns. */
	public int getColumns() { return columns; }
	
	/** Fetches the Lighthouse grid's rows. */
	public int getRows() { return rows; }
	
	@Override
	public Color getColorAt(IntVec lhGridPos) {
		Color overlayColor = overlayGrid.getColorAt(lhGridPos);
		return (overlayColor == null)
			? board.getColorAt(lighthouseToGrid.apply(lhGridPos).floor())
			: overlayColor;
	}
}
