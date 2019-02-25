package lighthouse.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lighthouse.util.ColorUtils;
import lighthouse.util.IntVec;

/**
 * A mutable brick.
 */
public class BrickBuilder implements Iterable<Direction>, GameBlock {
	private final List<Direction> brickGraph = new ArrayList<>();
	private final Color color = ColorUtils.randomColor();
	private final IntVec startPos;
	
	public BrickBuilder(IntVec startPos) {
		this.startPos = startPos;
	}
	
	public void append(Direction direction) { brickGraph.add(direction); }
	
	@Override
	public Color getColor() { return color; }
	
	public Brick build() { return new Brick(startPos, brickGraph, color); }
	
	@Override
	public IntVec getPos() { return startPos; }
	
	@Override
	public List<Direction> getStructure() { return brickGraph; }
	
	@Override
	public Iterator<Direction> iterator() { return brickGraph.iterator(); }
}
