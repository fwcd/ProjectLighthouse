package lighthouse.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

import lighthouse.model.Direction;

/**
 * A two-dimensional, immutable int-vector.
 */
public class IntVec implements Serializable {
	private static final long serialVersionUID = 5924492052517734738L;
	public static IntVec ZERO = new IntVec(0, 0);
	public static IntVec ONE_ONE = new IntVec(1, 1);
	
	private int x;
	private int y;
	
	/** Deserialization constructor. */
	protected IntVec() {}
	
	/** Creates a new two-dimensional int-vector. */
	public IntVec(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public IntVec add(IntVec other) { return new IntVec(x + other.x, y + other.y); }
	
	public IntVec add(int dx, int dy) { return new IntVec(x + dx, y + dy); }
	
	public IntVec add(Direction dir) { return new IntVec(x + dir.getDx(), y + dir.getDy()); }
	
	public IntVec sub(IntVec other) { return new IntVec(x - other.x, y - other.y); }
	
	public IntVec sub(int dx, int dy) { return new IntVec(x - dx, y - dy); }
	
	public IntVec sub(Direction dir) { return new IntVec(x - dir.getDx(), y - dir.getDy()); }
	
	public DoubleVec scale(double factor) { return new DoubleVec(x * factor, y * factor); }
	
	public DoubleVec scale(double xFactor, double yFactor) { return new DoubleVec(x * xFactor, y * yFactor); }
	
	public IntVec scale(int factor) { return new IntVec(x * factor, y * factor); }
	
	public IntVec scale(int xFactor, int yFactor) { return new IntVec(x * xFactor, y * yFactor); }
	
	public IntVec scale(IntVec other) { return new IntVec(x * other.x, y * other.y); }
	
	public DoubleVec toDouble() { return new DoubleVec(x, y); }
	
	public IntVec invert() { return new IntVec(-x, -y); }
	
	public IntVec abs() { return new IntVec(Math.abs(x), Math.abs(y)); }
	
	public IntVec signum() { return new IntVec(MathUtils.signum(x), MathUtils.signum(y)); }
	
	public IntVec min(IntVec other) { return new IntVec(Math.min(x, other.x), Math.min(y, other.y)); }
	
	public IntVec max(IntVec other) { return new IntVec(Math.max(x, other.x), Math.max(y, other.y)); }
	
	public IntVec withX(int newX) { return new IntVec(newX, y); }
	
	public IntVec withY(int newY) { return new IntVec(x, newY); }
	
	public int dot(IntVec other) { return (x * other.x) + (y * other.y); }
	
	public int cross(IntVec other) { return (x * other.y) - (y * other.x); }
	
	public double length() { return Math.sqrt((x * x) + (y * y)); }
	
	public boolean isPositive() { return x >= 0 && y >= 0; }
	
	public boolean isNegative() { return x <= 0 && y <= 0; }
	
	public boolean xIn(int startInclusive, int endExclusive) { return x >= startInclusive && x < endExclusive; }
	
	public boolean yIn(int startInclusive, int endExclusive) { return y >= startInclusive && y < endExclusive; }
	
	/** Efficiently determines whether this IntVec has length 0. */
	public boolean isZero() { return x == 0 && y == 0; }
	
	/** Efficiently determines whether this IntVec has length 1 (thus if it is a unit vector). */
	public boolean isUnit() { return (Math.abs(x) == 1 && y == 0) || (x == 0 && Math.abs(y) == 1); }
	
	/** Fetches the first directions from the possible "nearest" directions. */
	public Direction nearestDirection() {
		return nearestDirections().get(0);
	}
	
	/** Fetches all possible "nearest" directions. Potentially ambiguous. */
	public List<Direction> nearestDirections() {
		List<Direction> dirs = new ArrayList<>();
		if (-y >= Math.abs(x)) dirs.add(Direction.UP);
		if (-y <= -Math.abs(x)) dirs.add(Direction.DOWN);
		if (-y >= -x && -y <= x) dirs.add(Direction.RIGHT);
		if (-y >= x && -y <= -x) dirs.add(Direction.LEFT);
		return dirs;
	}
	
	/** Combines this int-vector with another using a binary operator. */
	public IntVec zip(IntVec other, IntBinaryOperator op) {
		return new IntVec(op.applyAsInt(x, other.x), op.applyAsInt(y, other.y));
	}
	
	/** Maps this int-vector over an unary operator. */
	public IntVec map(IntUnaryOperator op) {
		return new IntVec(op.applyAsInt(x), op.applyAsInt(y));
	}
	
	@Override
	public String toString() { return "[" + x + ", " + y + "]"; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		IntVec other = (IntVec) obj;
		return (x == other.x)
			&& (y == other.y);
	}
	
	@Override
	public int hashCode() {
		return 27 * x * y;
	}
}
