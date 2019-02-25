package lighthouse.model;

import java.util.*;

import lighthouse.util.IntVec;

/**
 * A block consisting of a list of directions.
 */
public class Brick {
	private final List<Direction> structure;
	private final List<Edge> edges = new ArrayList<>();
	
	private final Direction rotation;
	private IntVec pos;
	
	{
		edges.add(new Edge(IntVec.ZERO, Direction.UP));
		edges.add(new Edge(IntVec.ZERO, Direction.RIGHT));
		edges.add(new Edge(IntVec.ZERO, Direction.DOWN));
		edges.add(new Edge(IntVec.ZERO, Direction.LEFT));
	}
	
	public Brick(IntVec pos, List<Direction> structList) {
		this.pos = pos;
		this.rotation = Direction.UP;
		this.structure = structList;
		IntVec off = IntVec.ZERO;
		
		for (Direction dir : structList) {
			off = off.add(dir);
			IntVec tmpOff = off;
			
			for (Direction inDir : Direction.values()) {
				if (!this.edges.stream().anyMatch(edge -> edge.matches(tmpOff, dir))) {
					this.edges.add(new Edge(off, inDir.getOpposite()));
				} else {
					this.edges.removeIf(edge -> edge.matches(tmpOff, dir));
				}
			}
		}
	}
	
	@Override
	public int hashCode() {
		return structure.hashCode() * edges.hashCode() * rotation.hashCode() * pos.add(1, 1).hashCode() * 7;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Brick)) return false; 
		Brick brick = (Brick) obj;
		if (!pos.equals(brick.pos)) return false;
		return structure.equals(brick.structure);
	}
	
	public void moveBy(IntVec delta) { pos = pos.add(delta); }
	
	public void moveInto(Direction dir) { pos = pos.add(dir); }
	
	public IntVec getPos() { return pos; }
	
	public List<Edge> getEdges() { return edges; }
	
	public List<Direction> getStructure() { return structure; }
}
