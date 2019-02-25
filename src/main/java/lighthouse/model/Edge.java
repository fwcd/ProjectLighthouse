package lighthouse.model;

public class Edge {
	public int xOff;
	public int yOff;
	public Direction dir;
	public boolean highlighted = false;
	
	public Edge(int x, int y, Direction dir){
		this.xOff = x;
		this.yOff = y;
		this.dir = dir;
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
}
