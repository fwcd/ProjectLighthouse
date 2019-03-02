package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.grid.WritableColorGrid;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;
import lighthouse.util.MathUtils;

/**
 * A reference implementation of an {@link OverlayShapeVisitor}
 * that renders the shape to a {@link WritableColorGrid}.
 */
public class GridOverlayRenderer implements OverlayShapeVisitor {
	private static final Logger LOG = LoggerFactory.getLogger(GridOverlayRenderer.class);
	private static final double EPSILON = 0.0001;
	private final WritableColorGrid grid;
	private final Function<DoubleVec, IntVec> gridPosToPixels;
	private final Function<DoubleVec, IntVec> gridSizeToPixels;
	private boolean alphaEnabled = false;
	
	public GridOverlayRenderer(
		WritableColorGrid grid,
		Function<DoubleVec, IntVec> gridPosToPixels,
		Function<DoubleVec, IntVec> gridSizeToPixels
	) {
		this.grid = grid;
		this.gridPosToPixels = gridPosToPixels;
		this.gridSizeToPixels = gridSizeToPixels;
	}
	
	@Override
	public void visitFixedCircle(OverlayFixedCircle circle) {
		OverlayShading shading = circle.getShading();
		Color color = circle.getColor();
		IntVec center = gridPosToPixels.apply(circle.getCenter());
		IntVec radius = gridSizeToPixels.apply(new DoubleVec(circle.getRadius(), 0)).onlyXs()
			.min(gridSizeToPixels.apply(new DoubleVec(0, circle.getRadius())).onlyYs());
		
		drawOval(center.sub(radius), radius, shading, color);
	}
	
	@Override
	public void visitOval(OverlayOval oval) {
		OverlayShading shading = oval.getShading();
		Color color = oval.getColor();
		IntVec topLeft = gridPosToPixels.apply(oval.getTopLeft());
		IntVec radius = gridSizeToPixels.apply(oval.getRadius());
		
		LOG.trace("Mapped oval pos {} to rendered pos {}", oval.getTopLeft(), topLeft);
		drawOval(topLeft, radius, shading, color);
	}

	private void drawOval(IntVec topLeft, IntVec radius, OverlayShading shading, Color color) {
		IntVec size = radius.scale(2);
		IntVec squaredRadius = radius.square();
		
		switch (shading) {
		case FILLED:
			for (int y = 0; y < size.getY(); y++) {
				for (int x = 0; x < size.getX(); x++) {
					if (((double) MathUtils.square(x - radius.getX()) / squaredRadius.getX()) + ((double) MathUtils.square(y - radius.getY()) / squaredRadius.getY()) < 1) {
						drawColorAt(x + topLeft.getX(), y + topLeft.getY(), color);
					}
				}
			}
			break;
		case OUTLINED:
			double radiusRatio = radius.getY() / (double) radius.getX();
			
			for (int x = 0; x < size.getX(); x++) {
				double absY = radiusRatio * Math.sqrt(squaredRadius.getX() - MathUtils.square(x - radius.getX())) - EPSILON;
				drawColorAt(x + topLeft.getX(), (int) absY + radius.getY() + topLeft.getY(), color);
				drawColorAt(x + topLeft.getX(), (int) -absY + radius.getY() + topLeft.getY(), color);
			}
			break;
		default:
			throw invalidShading(shading);
		}
	}
	
	@Override
	public void visitRect(OverlayRect rect) {
		OverlayShading shading = rect.getShading();
		Color color = rect.getColor();
		IntVec topLeft = gridPosToPixels.apply(rect.getTopLeft());
		IntVec size = gridSizeToPixels.apply(rect.getSize());
		
		LOG.trace("Mapped rect pos {} to rendered pos {}", rect.getTopLeft(), topLeft);
		
		switch (shading) {
			case FILLED:
				LOG.trace("Drawing filled rect at {} of size {} onto the grid...", topLeft, size);
				for (int offY = 0; offY < size.getY(); offY++) {
					for (int offX = 0; offX < size.getX(); offX++) {
						drawColorAt(offX + topLeft.getX(), offY + topLeft.getY(), color);
					}
				}
				break;
			case OUTLINED:
				LOG.trace("Drawing outlined rect at {} of size {} onto the grid...", topLeft, size);
				for (int offX = 0; offX < size.getX(); offX++) {
					drawColorAt(topLeft.getX() + offX, topLeft.getY(), color);
					drawColorAt(topLeft.getX() + offX, topLeft.getY() + size.getY(), color);
				}
				for (int offY = 0; offY < size.getX(); offY++) {
					drawColorAt(topLeft.getX(), topLeft.getY() + offY, color);
					drawColorAt(topLeft.getX() + size.getX(), topLeft.getY() + offY, color);
				}
				break;
			default:
				throw invalidShading(shading);
		}
	}
	
	private void drawColorAt(int x, int y, Color color) {
		if (alphaEnabled) {
			grid.drawColorAt(x, y, color);
		} else {
			grid.setColorAt(x, y, color);
		}
	}
	
	private RuntimeException invalidShading(OverlayShading shading) {
		return new IllegalArgumentException("Invalid shading: " + shading);
	}
}
