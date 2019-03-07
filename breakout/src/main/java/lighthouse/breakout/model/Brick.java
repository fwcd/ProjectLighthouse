package lighthouse.breakout.model;

import lighthouse.util.Copyable;
import lighthouse.util.DoubleRect;

/** An immutable Breakout brick. */
public class Brick implements Copyable<Brick> {
	private final DoubleRect boundingBox;
	
	public Brick(DoubleRect boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public Brick(double x, double y, double width) {
		boundingBox = new DoubleRect(x, y, width, 1);
	}
	
	public DoubleRect getBoundingBox() {
		return boundingBox;
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
