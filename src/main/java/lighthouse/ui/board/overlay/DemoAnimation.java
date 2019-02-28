package lighthouse.ui.board.overlay;

import java.awt.Color;
import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

public class DemoAnimation implements Animation {
	@Override
	public int getTotalFrames() { return 100; }
	
	@Override
	public void drawHighRes(Graphics2D g2d, int frame) {
		g2d.setColor(Color.CYAN);
		g2d.fillRect(10, 10 + (frame * 3), 100, 50);
	}
	
	@Override
	public void drawLowRes(WritableColorGrid grid, int frame) {
		grid.drawRect(10, frame / 8, 4, 1, Color.CYAN);
	}
}
