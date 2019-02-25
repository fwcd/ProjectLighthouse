package lighthouse.model;

/**
 * A brick edge.
 */
public class Edge {
	private final int xOff;
	private final int yOff;
	private final Direction dir;
	private boolean highlighted = false;
	
	public Edge(int x, int y, Direction dir){
		this.xOff = x;
		this.yOff = y;
		this.dir = dir;
	}
	
	public boolean matches(int xOff, int yOff, Direction dir) {
		return (this.xOff == xOff)
			&& (this.yOff == yOff)
			&& (this.dir.equals(dir));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != getClass()) return false;
		if (obj == this) return true;
		Edge other = (Edge) obj;
		return (xOff == other.xOff)
			&& (yOff == other.yOff)
			&& (dir == other.dir)
			&& (highlighted == other.highlighted);
	}
	
	@Override
	public int hashCode() {
		return xOff * yOff * dir.hashCode() * (highlighted ? 1 : -1);
	}
	
	public int getXOff() { return xOff; }
	
	public int getYOff() { return yOff; }
	
	public Direction getDir() { return dir; }
	
	public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
}
