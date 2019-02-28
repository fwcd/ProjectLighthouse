package lighthouse.ui.board.overlay;

import java.awt.Color;
import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

public class DemoAnimation implements Animation {
	@Override
	public int getTotalFrames() { return 100; }
	
	@Override
	public void drawHighRes(Graphics2D g2d, int frame) {
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10, 10 + (frame * 2), 100, 50);
	}
	
	@Override
	public void drawLowRes(WritableColorGrid grid, int frame) {
		grid.drawRect(10, frame, 2, 1, Color.WHITE);
	}
}
