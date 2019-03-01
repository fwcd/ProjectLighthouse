package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

/**
 * An overlay that can be drawn in both high and low
 * resolution.
 */
@Deprecated
public interface Overlay {
	void drawHighRes(Graphics2D g2d);
	
	void drawLowRes(WritableColorGrid grid);
}
