package lighthouse.model;

import java.io.Serializable;

import lighthouse.util.Direction;
import lighthouse.util.IntVec;

/**
 * A brick edge.
 */
public class Edge implements Serializable {
	private static final long serialVersionUID = -2228402029013441765L;
	private IntVec off;
	private Direction dir;
	private transient boolean highlighted = false;
	
	/** Deserialization constructor. */
	protected Edge() {}
	
	public Edge(IntVec off, Direction dir) {
		this.off = off;
		this.dir = dir;
	}
	
	public boolean matches(IntVec off, Direction dir) {
		return this.off.equals(off)
			&& this.dir.equals(dir);
	}
	
	public boolean isDuplicateOf(Edge other) {
		return step().equals(other.off) && other.step().equals(off);
	}
	
	/** Steps into the direction the edge is facing. */
	public IntVec step() {
		return off.add(dir);
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
	
	public boolean isHighlighted() { return highlighted; }
	
	public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
	
	/** Deeply copies this edge. */
	public Edge copy() {
		Edge copied = new Edge(off, dir);
		copied.highlighted = highlighted;
		return copied;
	}
	
	@Override
	public String toString() { return dir.toArrow() + off.toString(); }
}
