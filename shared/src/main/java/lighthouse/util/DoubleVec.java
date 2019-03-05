package lighthouse.util;

import java.io.Serializable;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * A two-dimensional, immutable double-vector.
 */
public class DoubleVec implements Serializable {
	private static final long serialVersionUID = -7870207530459306953L;
	public static DoubleVec ZERO = new DoubleVec(0, 0);
	public static DoubleVec ONE_ONE = new DoubleVec(1, 1);
	
	private double x;
	private double y;
	
	/** Deserialization constructor. */
	protected DoubleVec() {}
	
	/** Creates a new two-dimensional double-vector. */
	public DoubleVec(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public DoubleVec add(DoubleVec other) { return new DoubleVec(x + other.x, y + other.y); }
	
	public DoubleVec add(double dx, double dy) { return new DoubleVec(x + dx, y + dy); }
	
	public DoubleVec add(Direction dir) { return new DoubleVec(x + dir.getDx(), y + dir.getDy()); }
	
	public DoubleVec sub(DoubleVec other) { return new DoubleVec(x - other.x, y - other.y); }
	
	public DoubleVec sub(double dx, double dy) { return new DoubleVec(x - dx, y - dy); }
	
	public DoubleVec sub(Direction dir) { return new DoubleVec(x - dir.getDx(), y - dir.getDy()); }
	
	public DoubleVec divide(double factor) { return new DoubleVec(x / factor, y / factor); }
	
	public DoubleVec divide(double xFactor, double yFactor) { return new DoubleVec(x / xFactor, y / yFactor); }
	
	public DoubleVec divide(DoubleVec other) { return new DoubleVec(x / other.getX(), y / other.getY()); }
	
	public DoubleVec scale(double factor) { return new DoubleVec(x * factor, y * factor); }
	
	public DoubleVec scale(double xFactor, double yFactor) { return new DoubleVec(x * xFactor, y * yFactor); }
	
	public DoubleVec scale(DoubleVec other) { return new DoubleVec(x * other.x, y * other.y); }
	
	public DoubleVec invert() { return new DoubleVec(-x, -y); }
	
	public DoubleVec abs() { return new DoubleVec(Math.abs(x), Math.abs(y)); }
	
	public IntVec round() { return new IntVec((int) Math.round(x), (int) Math.round(y)); }
	
	public IntVec castToInt() { return new IntVec((int) x, (int) y); }
	
	public IntVec floor() { return new IntVec((int) Math.floor(x), (int) Math.floor(y)); }
	
	public IntVec ceil() { return new IntVec((int) Math.ceil(x), (int) Math.ceil(y)); }
	
	public IntVec signum() { return new IntVec((int) Math.signum(x), (int) Math.signum(y)); }
	
	public DoubleVec square() { return new DoubleVec(x * x, y * y); }
	
	public DoubleVec min(DoubleVec other) { return new DoubleVec(Math.min(x, other.x), Math.min(y, other.y)); }
	
	public DoubleVec max(DoubleVec other) { return new DoubleVec(Math.max(x, other.x), Math.max(y, other.y)); }
	
	public DoubleVec withX(double newX) { return new DoubleVec(newX, y); }
	
	public DoubleVec withY(double newY) { return new DoubleVec(x, newY); }
	
	public DoubleVec normalize() { return divide(length()); }
	
	public double dot(DoubleVec other) { return (x * other.x) + (y * other.y); }
	
	public double cross(DoubleVec other) { return (x * other.y) - (y * other.x); }
	
	public double length() { return Math.sqrt((x * x) + (y * y)); }
	
	public boolean isPositive() { return x >= 0 && y >= 0; }
	
	public boolean isNegative() { return x <= 0 && y <= 0; }
	
	public boolean xIn(double startInclusive, double endExclusive) { return x >= startInclusive && x < endExclusive; }
	
	public boolean yIn(double startInclusive, double endExclusive) { return y >= startInclusive && y < endExclusive; }
	
	public boolean isZero() { return x == 0 && y == 0; }
	
	public Direction nearestDirection() {
		if (-y >= Math.abs(x)) return Direction.UP;
		if (-y <= -Math.abs(x)) return Direction.DOWN;
		if (-y >= -x && -y < x) return Direction.RIGHT;
		if (-y >= x && -y < -x) return Direction.LEFT;
		throw new IllegalStateException("Will never happen");
	}
	
	/** Combines this double-vector with another using a binary operator. */
	public DoubleVec zip(DoubleVec other, DoubleBinaryOperator op) {
		return new DoubleVec(op.applyAsDouble(x, other.x), op.applyAsDouble(y, other.y));
	}
	
	/** Maps this double-vector over an unary operator. */
	public DoubleVec map(DoubleUnaryOperator op) {
		return new DoubleVec(op.applyAsDouble(x), op.applyAsDouble(y));
	}
	
	/** Checks whether the two vectors are equal within a given tolerance. */
	public boolean equals(DoubleVec other, double eps) {
		return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps;
	}
	
	@Override
	public String toString() { return "[" + x + ", " + y + "]"; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		DoubleVec other = (DoubleVec) obj;
		return (x == other.x)
			&& (y == other.y);
	}
	
	@Override
	public int hashCode() {
		return (int) (27 * x * y);
	}
}
