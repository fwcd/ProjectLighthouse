package lighthouse.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lighthouse.util.ColorUtils;
import lighthouse.util.Direction;
import lighthouse.util.IDGenerator;
import lighthouse.util.IntVec;

/**
 * A mutable brick.
 */
public class BrickBuilder implements Iterable<Direction>, GameBlock {
	private final List<Direction> structure;
	private final Color color;
	private final int id;
	private IntVec startPos;
	
	public BrickBuilder(Brick brick) {
		structure = new ArrayList<>(brick.getStructure());
		startPos = brick.getPos();
		color = brick.getColor();
		id = brick.getID();
	}
	
	public BrickBuilder(IntVec startPos) {
		structure = new ArrayList<>();
		this.startPos = startPos;
		color = ColorUtils.randomColor();
		id = IDGenerator.INSTANCE.nextID();
	}
	
	public void append(Direction direction) { structure.add(direction); }
	
	public void moveBy(IntVec delta) { startPos = startPos.add(delta); }
	
	@Override
	public Color getColor() { return color; }
	
	public Brick build() { return new Brick(startPos, structure, color, id); }
	
	@Override
	public IntVec getPos() { return startPos; }
	
	@Override
	public List<Direction> getStructure() { return structure; }
	
	@Override
	public Iterator<Direction> iterator() { return structure.iterator(); }
}
