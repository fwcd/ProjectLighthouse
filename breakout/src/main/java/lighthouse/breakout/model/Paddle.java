package lighthouse.breakout.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;

public class Paddle implements BoxBounded {
	private DoubleRect boundingBox;
	
	public Paddle(double x, double y, double width) {
		boundingBox = new DoubleRect(x, y, width, 1);
	}
	
	public void move(double dx) {
		boundingBox = boundingBox.movedBy(dx, 0);
	}
	
	@Override
	public DoubleRect getBoundingBox() {
		return boundingBox;
	}
}
