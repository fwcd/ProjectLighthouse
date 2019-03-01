package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

@Deprecated
public interface Animation {
	int getTotalFrames();
	
	void drawHighRes(Graphics2D g2d, int frame);
	
	void drawLowRes(WritableColorGrid grid, int frame);
}
