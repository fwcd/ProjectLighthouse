package lighthouse.util;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

import lighthouse.model.Direction;

/**
 * A two-dimensional, immutable int-vector.
 */
public class IntVec {
	private final int x;
	private final int y;
	
	public IntVec(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public IntVec add(IntVec other) { return new IntVec(x + other.x, y + other.y); }
	
	public IntVec sub(IntVec other) { return new IntVec(x - other.y, y - other.y); }
	
	public IntVec scale(double factor) { return new IntVec((int) (x * factor), (int) (y * factor)); }
	
	public int dot(IntVec other) { return (x * other.x) + (y * other.y); }
	
	public int cross(IntVec other) { return (x * other.y) - (y * other.x); }
	
	public double length() { return Math.sqrt((x * x) + (y * y)); }
	
	public Direction getNearestDirection() {
		if (y >= Math.abs(x))  return Direction.UP;
		if (y <= -Math.abs(x)) return Direction.DOWN;
		if (y >= -x && y < x)  return Direction.RIGHT;
		if (y >= x && y < -x)  return Direction.LEFT;
		throw new IllegalStateException("Will never happen");
	}
	
	public IntVec zip(IntVec other, IntBinaryOperator op) {
		return new IntVec(op.applyAsInt(x, other.x), op.applyAsInt(y, other.y));
	}
	
	public IntVec map(IntUnaryOperator op) {
		return new IntVec(op.applyAsInt(x), op.applyAsInt(y));
	}
}
