package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Graphics2D;
import java.util.function.Function;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

/**
 * A {@link Graphics2D}-based shape renderer.
 */
public class Graphics2DSceneRenderer implements SceneShapeVisitor {
	private final Graphics2D g2d;
	private final Function<DoubleVec, IntVec> gridPosToPixels;
	private final Function<DoubleVec, IntVec> gridSizeToPixels;

	public Graphics2DSceneRenderer(Graphics2D g2d, Function<DoubleVec, IntVec> gridToPixels) {
		this(g2d, gridToPixels, gridToPixels);
	}
	
	public Graphics2DSceneRenderer(
		Graphics2D g2d,
		Function<DoubleVec, IntVec> gridPosToPixels,
		Function<DoubleVec, IntVec> gridSizeToPixels
	) {
		this.g2d = g2d;
		this.gridPosToPixels = gridPosToPixels;
		this.gridSizeToPixels = gridSizeToPixels;
	}

	@Override
	public void visitRect(SceneRect rect) {
		g2d.setColor(rect.getColor());

		Shading shading = rect.getShading();
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
	public void visitFixedCircle(SceneFixedCircle circle) {
		g2d.setColor(circle.getColor());

		Shading shading = circle.getShading();
		IntVec topLeft = gridPosToPixels.apply(circle.getTopLeft());
		IntVec radius = gridSizeToPixels.apply(new DoubleVec(circle.getRadius(), 0)).onlyXs()
			.min(gridSizeToPixels.apply(new DoubleVec(0, circle.getRadius())).onlyYs());
		
		drawOval(topLeft, radius.scale(2), shading);
	}

	@Override
	public void visitOval(SceneOval oval) {
		g2d.setColor(oval.getColor());

		Shading shading = oval.getShading();
		IntVec topLeft = gridPosToPixels.apply(oval.getTopLeft());
		IntVec size = gridSizeToPixels.apply(oval.getSize());

		drawOval(topLeft, size, shading);
	}

	@Override
	public void visitImage(SceneImage image) {
		IntVec topLeft = gridPosToPixels.apply(image.getTopLeft());
		IntVec size = gridSizeToPixels.apply(image.getImageSize());
		
		g2d.drawImage(image.getImage(), topLeft.getX(), topLeft.getY(), size.getX(), size.getY(), null);
	}
	
	private void drawOval(IntVec topLeft, IntVec size, Shading shading) {
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

	private RuntimeException invalidShading(Shading shading) {
		return new IllegalArgumentException("Invalid shading: " + shading);
	}
}
