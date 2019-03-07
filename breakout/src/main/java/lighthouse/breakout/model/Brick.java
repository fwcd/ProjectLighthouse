package lighthouse.breakout.model;

import java.awt.Color;

import lighthouse.util.ColorUtils;
import lighthouse.util.Copyable;
import lighthouse.util.DoubleRect;

/** An immutable Breakout brick. */
public class Brick implements Copyable<Brick>, BoundingBoxable {
	private final DoubleRect boundingBox;
	private final Color color = ColorUtils.randomColor();
	
	public Brick(DoubleRect boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public Brick(double x, double y, double width) {
		boundingBox = new DoubleRect(x, y, width, 1);
	}
	
	@Override
	public DoubleRect getBoundingBox() {
		return boundingBox;
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Copies this brick. In almost all cases, however,
	 * this operation is unnecessary since bricks are immutable.
	 */
	@Override
	public Brick copy() {
		return new Brick(boundingBox);
	}
}
