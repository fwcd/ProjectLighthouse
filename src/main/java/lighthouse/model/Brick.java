package lighthouse.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.util.ColorUtils;
import lighthouse.util.IntVec;

/**
 * A positioned game brick consisting
 * a list of directions and edges. This internal,
 * "arrow-based" representation ensures that
 * all brick fragments are connected.
 * 
 * <p>Note that bricks are immutable.</p>
 */
public class Brick implements GameBlock, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(Brick.class);
	private static final long serialVersionUID = -4396959159634915799L;
	private List<Direction> structure;
	private List<Edge> edges = new ArrayList<>();
	
	private Color color;
	private IntVec pos;
	
	{
		edges.add(new Edge(IntVec.ZERO, Direction.UP));
		edges.add(new Edge(IntVec.ZERO, Direction.RIGHT));
		edges.add(new Edge(IntVec.ZERO, Direction.DOWN));
		edges.add(new Edge(IntVec.ZERO, Direction.LEFT));
	}
	
	/** Deserialization constructor. */
	protected Brick() {}
	
	public Brick(IntVec pos, List<Direction> structure) {
		this(pos, structure, ColorUtils.randomColor());
	}
	
	public Brick(IntVec pos, List<Direction> structure, Color color) {
		this.pos = pos;
		this.color = color;
		this.structure = structure;
		
		findEdges();
	}
	
	private void findEdges() {
		IntVec off = IntVec.ZERO;
		
		LOG.debug("Initial edges: {}", edges);
		
		// Traverse the structure
		for (Direction dir : structure) {
			off = off.add(dir);
			
			// Initially add all directions
			for (Direction inDir : Direction.values()) {
				edges.add(new Edge(off, inDir));
			}
		}
		
		// Remove all pairs of edges that are duplicated
		List<Edge> removed = new ArrayList<>();
		
		for (int i = 0; i < edges.size(); i++) {
			for (int j = i + 1; j < edges.size(); j++) {
				Edge a = edges.get(i);
				Edge b = edges.get(j);
				
				if (a.isDuplicateOf(b)) {
					LOG.debug("{} and {} are duplicates", a, b);
					removed.add(a);
					removed.add(b);
				}
			}
		}
		
		edges.removeAll(removed);
		
		LOG.info("Found edges {}", edges);
	}
	
	/** Tests whether two bricks intersect. */
	public boolean intersects(GameBlock other) {
		return !Collections.disjoint(getOccupiedPositions(), other.getOccupiedPositions());
	}
	
	@Override
	public int hashCode() {
		return structure.hashCode() * edges.hashCode() * pos.add(1, 1).hashCode() * 7;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Brick)) return false; 
		Brick brick = (Brick) obj;
		if (!pos.equals(brick.pos)) return false;
		return structure.equals(brick.structure);
	}
	
	public Brick movedBy(IntVec delta) { return new Brick(pos.add(delta), structure, color); }
	
	public Brick movedInto(Direction dir) { return new Brick(pos.add(dir), structure, color); }
	
	@Override
	public IntVec getPos() { return pos; }
	
	public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
	
	@Override
	public List<Direction> getStructure() { return Collections.unmodifiableList(structure); }
	
	@Override
	public Color getColor() { return color; }
	
	@Override
	public String toString() { return "Brick [pos=" + pos + ", color=#" + Integer.toHexString(color.getRGB()) + ", structure=" + structure + ", edges=" + edges + "]"; }
}
