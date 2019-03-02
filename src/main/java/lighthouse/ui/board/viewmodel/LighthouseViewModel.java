package lighthouse.ui.board.viewmodel;

import java.awt.Color;

import lighthouse.model.grid.ArrayColorGrid;
import lighthouse.model.grid.ColorGrid;
import lighthouse.model.grid.WritableColorGrid;
import lighthouse.ui.board.viewmodel.overlay.GridOverlayRenderer;
import lighthouse.ui.board.viewmodel.overlay.Overlay;
import lighthouse.ui.board.viewmodel.overlay.OverlayShapeVisitor;
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
	private final DoubleVecBijection lighthouseSizeToGrid = new Scaling(0.2, 0.5);
	private final DoubleVecBijection lighthousePosToGrid = new Translation(-4, -1).andThen(lighthouseSizeToGrid);
	
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
	
	private void renderOverlays() {
		OverlayShapeVisitor renderer = new GridOverlayRenderer(
			overlayGrid,
			lighthousePosToGrid.inverse().floor(),
			lighthouseSizeToGrid.inverse().ceil()
		);
		overlayGrid.clear();
		
		for (Overlay overlay : board.getOverlays()) {
			overlay.acceptForAllShapes(renderer);
		}
	}
	
	@Override
	public Color getColorAt(IntVec gridPos) {
		renderOverlays();
		Color overlayColor = overlayGrid.getColorAt(gridPos);
		return (overlayColor == null)
			? board.getColorAt(lighthousePosToGrid.apply(gridPos).floor())
			: overlayColor;
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
}
