package lighthouse.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lighthouse.util.ColorUtils;
import lighthouse.util.IDGenerator;
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
	private static final long serialVersionUID = -4396959159634915799L;
	/** An id that is preserved through move copies. */
	private int id;
	private List<Direction> structure;
	private List<Edge> edges;
	
	private Color color;
	private IntVec pos;
	
	/** Deserialization constructor. */
	protected Brick() {}
	
	public Brick(IntVec pos, List<Direction> structure) {
		this(pos, structure, ColorUtils.randomColor());
	}
	
	public Brick(IntVec pos, List<Direction> structure, Color color) {
		this(pos, structure, color, IDGenerator.INSTANCE.nextID());
	}
	
	public Brick(IntVec pos, List<Direction> structure, Color color, int id) {
		this.pos = pos;
		this.color = color;
		this.structure = structure;
		this.id = id;
		edges = computeEdges();
	}
	
	/** Tests whether two bricks intersect. */
	public boolean intersects(GameBlock other) {
		return !Collections.disjoint(getOccupiedPositions(), other.getOccupiedPositions());
	}
	
	@Override
	public int hashCode() {
		return structure.hashCode() * pos.hashCode() * 7;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Brick)) return false; 
		Brick brick = (Brick) obj;
		return structure.equals(brick.structure) && pos.equals(brick.pos);
	}
	
	public boolean matchesIDOf(Brick brick) {
		return id == brick.id;
	}
	
	/** Fetches the brick id. */
	public int getID() { return id; }
	
	public Brick movedBy(IntVec delta) { return new Brick(pos.add(delta), structure, color, id); }
	
	public Brick movedInto(Direction dir) { return new Brick(pos.add(dir), structure, color, id); }
	
	@Override
	public IntVec getPos() { return pos; }
	
	@Override
	public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
	
	@Override
	public List<Direction> getStructure() { return Collections.unmodifiableList(structure); }
	
	@Override
	public Color getColor() { return color; }
	
	@Override
	public String toString() { return "Brick [pos=" + pos + ", color=#" + Integer.toHexString(color.getRGB()) + ", structure=" + structure + ", edges=" + edges + "]"; }
}
