package lighthouse.util;

/**
 * An axis-aligned rectangle on a 2D integer grid.
 */
public class IntRect {
	private final IntVec topLeft;
	private final IntVec size;
	
	public IntRect(int x, int y, int width, int height) {
		this(new IntVec(x, y), width, height);
	}
	
	public IntRect(IntVec topLeft, int width, int height) {
		this(topLeft, new IntVec(width, height));
	}
	
	public IntRect(IntVec topLeft, IntVec size) {
		this.topLeft = topLeft;
		this.size = size;
	}
	
	public IntRect movedBy(IntVec offset) {
		return new IntRect(topLeft.add(offset), size);
	}
	
	public IntRect movedBy(int dx, int dy) {
		return new IntRect(topLeft.add(dx, dy), size);
	}
	
	public IntVec getTopLeft() {
		return topLeft;
	}
	
	public IntVec getSize() {
		return size;
	}
	
	public IntVec getCenter() {
		return topLeft.add(size.getX() / 2, size.getY() / 2);
	}
	
	public IntVec getBottomRight() {
		return topLeft.add(size);
	}
	
	public DoubleRect toDouble() {
		return new DoubleRect(topLeft.toDouble(), size.toDouble());
	}
	
	public boolean contains(IntVec pos) {
		return contains(pos.getX(), pos.getY());
	}
	
	public boolean contains(int x, int y) {
		int minX = topLeft.getX();
		int minY = topLeft.getY();
		int maxX = minX + size.getX();
		int maxY = minY + size.getY();
		return (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
	}
	
	public boolean intersects(IntRect other) {
		int ax0 = topLeft.getX();
		int ay0 = topLeft.getY();
		int ax1 = topLeft.getX() + size.getX();
		int ay1 = topLeft.getY() + size.getY();
		int bx0 = other.topLeft.getX();
		int by0 = other.topLeft.getY();
		int bx1 = other.topLeft.getX() + other.size.getX();
		int by1 = other.topLeft.getY() + other.size.getY();
		return (bx1 >= ax0) && (bx0 <= ax1) && (by1 >= ay0) && (by0 <= ay1);
	}
	
	@Override
	public String toString() {
		return "IntRect [topLeft=" + topLeft + ", size=" + size + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		IntRect other = (IntRect) obj;
		return topLeft.equals(other.topLeft)
			&& size.equals(other.size);
	}
	
	@Override
	public int hashCode() {
		return topLeft.hashCode() * size.hashCode() * 7;
	}
}
