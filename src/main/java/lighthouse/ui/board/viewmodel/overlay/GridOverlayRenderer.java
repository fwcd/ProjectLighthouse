package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.grid.WritableColorGrid;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

/**
 * A reference implementation of an {@link OverlayShapeVisitor}
 * that renders the shape to a {@link WritableColorGrid}.
 */
public class GridOverlayRenderer implements OverlayShapeVisitor {
	private static final Logger LOG = LoggerFactory.getLogger(GridOverlayRenderer.class);
	private final WritableColorGrid grid;
	private final Function<DoubleVec, IntVec> gridPosToPixels;
	private final Function<DoubleVec, IntVec> gridSizeToPixels;
	
	public GridOverlayRenderer(WritableColorGrid grid, Function<DoubleVec, IntVec> gridPosToPixels, Function<DoubleVec, IntVec> gridSizeToPixels) {
		this.grid = grid;
		this.gridPosToPixels = gridPosToPixels;
		this.gridSizeToPixels = gridSizeToPixels;
	}
	
	@Override
	public void visitOval(OverlayOval oval) {
		// TODO
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
						LOG.trace("Rasterizing filled rect {} at [{}, {}]", topLeft, offX, offY);
						grid.setColorAt(offX + topLeft.getX(), offY + topLeft.getY(), color);
					}
				}
				break;
			case OUTLINED:
				LOG.trace("Drawing outlined rect at {} of size {} onto the grid...", topLeft, size);
				for (int offX = 0; offX < size.getX(); offX++) {
					LOG.trace("Rasterizing horizontal rect outline {} at x = {}", topLeft, offX);
					grid.setColorAt(topLeft.getX() + offX, topLeft.getY(), color);
					grid.setColorAt(topLeft.getX() + offX, topLeft.getY() + size.getY(), color);
				}
				for (int offY = 0; offY < size.getX(); offY++) {
					LOG.trace("Rasterizing vertical rect outline {} at y = {}", topLeft, offY);
					grid.setColorAt(topLeft.getX(), topLeft.getY() + offY, color);
					grid.setColorAt(topLeft.getX() + size.getX(), topLeft.getY() + offY, color);
				}
				break;
			default:
				throw invalidShading(shading);
		}
	}

	private RuntimeException invalidShading(OverlayShading shading) {
		return new IllegalArgumentException("Invalid shading: " + shading);
	}
}
