package lighthouse.util;

/**
 * An axis-aligned rectangle on the 2D plane.
 */
public class DoubleRect {
	private final DoubleVec topLeft;
	private final DoubleVec size;
	
	public DoubleRect(double x, double y, double width, double height) {
		this(new DoubleVec(x, y), width, height);
	}
	
	public DoubleRect(DoubleVec topLeft, double width, double height) {
		this(topLeft, new DoubleVec(width, height));
	}
	
	public DoubleRect(DoubleVec topLeft, DoubleVec size) {
		this.topLeft = topLeft;
		this.size = size;
	}
	
	public DoubleRect movedBy(DoubleVec offset) {
		return new DoubleRect(topLeft.add(offset), size);
	}
	
	public DoubleRect movedBy(double dx, double dy) {
		return new DoubleRect(topLeft.add(dx, dy), size);
	}
	
	public DoubleVec getTopLeft() {
		return topLeft;
	}
	
	public DoubleVec getSize() {
		return size;
	}
	
	public DoubleVec getBottomRight() {
		return topLeft.add(size);
	}
	
	public DoubleVec getCenter() {
		return topLeft.add(size.getX() / 2, size.getY() / 2);
	}
	
	public boolean contains(DoubleVec pos) {
		return contains(pos.getX(), pos.getY());
	}
	
	public boolean contains(double x, double y) {
		double minX = topLeft.getX();
		double minY = topLeft.getY();
		double maxX = minX + size.getX();
		double maxY = minY + size.getY();
		return (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
	}
	
	public boolean intersects(DoubleRect other) {
		double ax0 = topLeft.getX();
		double ay0 = topLeft.getY();
		double ax1 = topLeft.getX() + size.getX();
		double ay1 = topLeft.getY() + size.getY();
		double bx0 = other.topLeft.getX();
		double by0 = other.topLeft.getY();
		double bx1 = other.topLeft.getX() + other.size.getX();
		double by1 = other.topLeft.getY() + other.size.getY();
		return (bx1 >= ax0) && (bx0 <= ax1) && (by1 >= ay0) && (by0 <= ay1);
	}
	
	@Override
	public String toString() {
		return "DoubleRect [topLeft=" + topLeft + ", size=" + size + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		DoubleRect other = (DoubleRect) obj;
		return topLeft.equals(other.topLeft)
			&& size.equals(other.size);
	}
	
	@Override
	public int hashCode() {
		return topLeft.hashCode() * size.hashCode() * 7;
	}
}
