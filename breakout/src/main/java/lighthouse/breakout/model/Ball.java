package lighthouse.breakout.model;

import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Ball {
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
	
	public DoubleRect getBoundingBox() {
		return new DoubleRect(position.sub(radius, radius), radius, radius);
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
