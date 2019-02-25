package lighthouse.model;

import lighthouse.util.IntVec;

/**
 * A brick edge.
 */
public class Edge {
	private final IntVec off;
	private final Direction dir;
	private boolean highlighted = false;
	
	public Edge(IntVec off, Direction dir) {
		this.off = off;
		this.dir = dir;
	}
	
	public boolean matches(IntVec off, Direction dir) {
		return this.off.equals(off)
			&& this.dir.equals(dir);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != getClass()) return false;
		if (obj == this) return true;
		Edge other = (Edge) obj;
		return off.equals(other.off)
			&& (dir == other.dir)
			&& (highlighted == other.highlighted);
	}
	
	@Override
	public int hashCode() {
		return off.hashCode() * dir.hashCode() * (highlighted ? 1 : -1);
	}
	
	public IntVec getOff() { return off; }
	
	public Direction getDir() { return dir; }
	
	public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
}
