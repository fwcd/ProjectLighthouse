package lighthouse.ui.board.view;

import java.awt.Graphics2D;
import java.util.function.Function;

import lighthouse.ui.board.viewmodel.overlay.OverlayFixedCircle;
import lighthouse.ui.board.viewmodel.overlay.OverlayOval;
import lighthouse.ui.board.viewmodel.overlay.OverlayRect;
import lighthouse.ui.board.viewmodel.overlay.OverlayShading;
import lighthouse.ui.board.viewmodel.overlay.OverlayShapeVisitor;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class LocalBoardOverlayRenderer implements OverlayShapeVisitor {
	private final Graphics2D g2d;
	private final Function<DoubleVec, IntVec> gridToPixels;

	public LocalBoardOverlayRenderer(Graphics2D g2d, Function<DoubleVec, IntVec> gridToPixels) {
		this.g2d = g2d;
		this.gridToPixels = gridToPixels;
	}

	@Override
	public void visitRect(OverlayRect rect) {
		g2d.setColor(rect.getColor());

		OverlayShading shading = rect.getShading();
		IntVec topLeft = gridToPixels.apply(rect.getTopLeft());
		IntVec size = gridToPixels.apply(rect.getSize());

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
		IntVec topLeft = gridToPixels.apply(circle.getTopLeft());
		IntVec radius = gridToPixels.apply(new DoubleVec(circle.getRadius(), 0)).onlyXs()
			.min(gridToPixels.apply(new DoubleVec(0, circle.getRadius())).onlyYs());
		
		drawOval(topLeft, radius.scale(2), shading);
	}

	@Override
	public void visitOval(OverlayOval oval) {
		g2d.setColor(oval.getColor());

		OverlayShading shading = oval.getShading();
		IntVec topLeft = gridToPixels.apply(oval.getTopLeft());
		IntVec size = gridToPixels.apply(oval.getSize());

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
