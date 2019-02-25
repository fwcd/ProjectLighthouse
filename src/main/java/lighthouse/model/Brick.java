package lighthouse.model;

import java.util.*;

import lighthouse.util.IntVec;

/**
 * A positioned game brick consisting
 * a list of directions and edges. This internal,
 * "arrow-based" representation ensures that
 * all brick fragments are connected.
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
	
	public Brick(IntVec pos, List<Direction> structure) {
		this.pos = pos;
		this.rotation = Direction.UP;
		this.structure = structure;
		IntVec off = IntVec.ZERO;
		
		for (Direction dir : structure) {
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
	
	/** Traverses the brick to check for containment. */
	public boolean contains(IntVec checkedPos) {
		IntVec current = pos;
		if (current.equals(checkedPos)) return true;
		
		for (Direction dir : structure) {
			current = current.add(dir);
			if (current.equals(checkedPos)) return true;
		}
		
		return false;
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
	
	public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
	
	public List<Direction> getStructure() { return Collections.unmodifiableList(structure); }
	
	/** Deeply copies this brick. */
	public Brick copy() {
		return new Brick(pos, new ArrayList<>(structure));
	}
}
