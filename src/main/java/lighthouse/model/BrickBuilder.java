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
public class BrickBuilder implements Iterable<Direction> {
	private final List<Direction> brickGraph = new ArrayList<>();
	private final Color color = ColorUtils.randomColor();
	private final IntVec start;
	
	public BrickBuilder(IntVec start) {
		this.start = start;
	}
	
	public void append(Direction direction) { brickGraph.add(direction); }
	
	public Color getColor() { return color; }
	
	public Brick build() { return new Brick(start, brickGraph, color); }
	
	@Override
	public Iterator<Direction> iterator() { return brickGraph.iterator(); }
}
