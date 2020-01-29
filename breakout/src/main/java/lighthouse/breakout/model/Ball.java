package lighthouse.breakout.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Ball implements BoxBounded {
	private final double radius;
	private DoubleVec position;
	private DoubleVec direction;
	
	public Ball(int x, int y, double initialSpeed, double radius) {
		this.radius = radius;
		position = new DoubleVec(x, y);
		direction = DoubleVec.randomPolarVector(initialSpeed);
	}
	
	public void move() {
		position = position.add(direction);
	}
	
	/** Bounces off a vertical wall. */
	public void bounceOffVerticalObstacle() {
		direction = direction.invertX();
	}
	
	/** Bounces off a horizontal wall. */
	public void bounceOffHorizontalObstacle() {
		direction = direction.invertY();
	}
	
	public boolean isOutOfBounds(double width, double height) {
		return isOutOfHorizontalBounds(width) && isOutOfVerticalBounds(height);
	}
	
	public boolean isOutOfHorizontalBounds(double width) {
		return (position.getX() - radius) < 0 || (position.getX() + radius) > width;
	}
	
	public boolean isOutOfVerticalBounds(double height) {
		return (position.getY() - radius) < 0 || (position.getY() + radius) > height;
	}
	
	@Override
	public DoubleRect getBoundingBox() {
		return new DoubleRect(position.sub(radius, radius), radius * 2, radius * 2);
	}
	
	public DoubleVec getPosition() {
		return position;
	}
	
	public DoubleVec getDirection() {
		return direction;
	}

	public double getRadius() {
		return radius;
	}
}
