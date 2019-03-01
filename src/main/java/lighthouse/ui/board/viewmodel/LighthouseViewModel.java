package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lighthouse.model.grid.ArrayColorGrid;
import lighthouse.model.grid.ColorGrid;
import lighthouse.model.grid.WritableColorGrid;
import lighthouse.ui.board.overlay.Overlay;
import lighthouse.util.IntVec;
import lighthouse.util.LhConstants;

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
	private final List<Overlay> overlays = new ArrayList<>();
	
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
	
	public void renderOverlays() {
		overlayGrid.clear();
		for (Overlay overlay : overlays) {
			overlay.drawLowRes(overlayGrid);
		}
	}
	
	public void addOverlay(Overlay overlay) { overlays.add(overlay); }
	
	public void removeOverlay(Overlay overlay) { overlays.remove(overlay); }
	
	@Override
	public Color getColorAt(IntVec lhGridPos) {
		Color overlayColor = overlayGrid.getColorAt(lhGridPos);
		return (overlayColor == null)
			? board.getColorAt(lhGridPos.sub(4, 1).scale(0.2, 0.5).floor())
			: overlayColor;
	}
}
