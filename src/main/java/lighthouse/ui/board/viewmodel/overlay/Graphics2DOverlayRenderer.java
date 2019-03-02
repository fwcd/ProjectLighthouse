package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Graphics2D;
import java.util.function.Function;

import lighthouse.ui.board.viewmodel.overlay.OverlayFixedCircle;
import lighthouse.ui.board.viewmodel.overlay.OverlayOval;
import lighthouse.ui.board.viewmodel.overlay.OverlayRect;
import lighthouse.ui.board.viewmodel.overlay.OverlayShading;
import lighthouse.ui.board.viewmodel.overlay.OverlayShapeVisitor;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class Graphics2DOverlayRenderer implements OverlayShapeVisitor {
	private final Graphics2D g2d;
	private final Function<DoubleVec, IntVec> gridPosToPixels;
	private final Function<DoubleVec, IntVec> gridSizeToPixels;

	public Graphics2DOverlayRenderer(Graphics2D g2d, Function<DoubleVec, IntVec> gridToPixels) {
		this(g2d, gridToPixels, gridToPixels);
	}
	
	public Graphics2DOverlayRenderer(
		Graphics2D g2d,
		Function<DoubleVec, IntVec> gridPosToPixels,
		Function<DoubleVec, IntVec> gridSizeToPixels
	) {
		this.g2d = g2d;
		this.gridPosToPixels = gridPosToPixels;
		this.gridSizeToPixels = gridSizeToPixels;
	}

	@Override
	public void visitRect(OverlayRect rect) {
		g2d.setColor(rect.getColor());

		OverlayShading shading = rect.getShading();
		IntVec topLeft = gridPosToPixels.apply(rect.getTopLeft());
		IntVec size = gridSizeToPixels.apply(rect.getSize());

		switch (shading) {
		case FILLED:
			g2d.fillRect(topLeft.getX(), topLeft.getY(), size.getX(), size.getY());
			break;
		case OUTLINED:
			g2d.drawRect(topLeft.getX(), topLeft.getY(), size.getX(), size.getY());
			break;
		default:
			throw invalidShading(shading);
		}
	}
	
	@Override
	public void visitFixedCircle(OverlayFixedCircle circle) {
		g2d.setColor(circle.getColor());

		OverlayShading shading = circle.getShading();
		IntVec topLeft = gridPosToPixels.apply(circle.getTopLeft());
		IntVec radius = gridSizeToPixels.apply(new DoubleVec(circle.getRadius(), 0)).onlyXs()
			.min(gridSizeToPixels.apply(new DoubleVec(0, circle.getRadius())).onlyYs());
		
		drawOval(topLeft, radius.scale(2), shading);
	}

	@Override
	public void visitOval(OverlayOval oval) {
		g2d.setColor(oval.getColor());

		OverlayShading shading = oval.getShading();
		IntVec topLeft = gridPosToPixels.apply(oval.getTopLeft());
		IntVec size = gridSizeToPixels.apply(oval.getSize());

		drawOval(topLeft, size, shading);
	}

	private void drawOval(IntVec topLeft, IntVec size, OverlayShading shading) {
		switch (shading) {
		case FILLED:
			g2d.fillOval(topLeft.getX(), topLeft.getY(), size.getX(), size.getY());
			break;
		case OUTLINED:
			g2d.drawOval(topLeft.getX(), topLeft.getY(), size.getX(), size.getY());
			break;
		default:
			throw invalidShading(shading);
		}
	}

	private RuntimeException invalidShading(OverlayShading shading) {
		return new IllegalArgumentException("Invalid shading: " + shading);
	}
}
