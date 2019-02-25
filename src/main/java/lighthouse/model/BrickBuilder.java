package lighthouse.model;

import java.util.ArrayList;
import java.util.List;

import lighthouse.util.IntVec;

/**
 * A mutable brick.
 */
public class BrickBuilder {
	private final List<Direction> brickGraph = new ArrayList<>();
	private final IntVec start;
	
	public BrickBuilder(IntVec start) {
		this.start = start;
	}
	
	public void append(Direction direction) {
		brickGraph.add(direction);
	}
	
	public Brick build() {
		return new Brick(start, brickGraph);
	}
}
