package lighthouse.gameapi;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface Renderable {
	void render(Graphics2D g2d, Dimension canvasSize);
}
