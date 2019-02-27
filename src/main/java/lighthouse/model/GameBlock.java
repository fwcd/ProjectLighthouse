package lighthouse.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.util.IntVec;

/**
 * Common interface for colored game elements that
 * are structured using connected arrows
 * and a start position.
 */
public interface GameBlock {
	Logger LOG = LoggerFactory.getLogger(GameBlock.class);
	
	List<? extends Direction> getStructure();
	
	IntVec getPos();
	
	Color getColor();
	
	default List<? extends Edge> getEdges() { return computeEdges(); }
	
	default List<Edge> computeEdges() {
		List<Edge> edges = new ArrayList<>();
		
		edges.add(new Edge(IntVec.ZERO, Direction.UP));
		edges.add(new Edge(IntVec.ZERO, Direction.RIGHT));
		edges.add(new Edge(IntVec.ZERO, Direction.DOWN));
		edges.add(new Edge(IntVec.ZERO, Direction.LEFT));
		
		IntVec off = IntVec.ZERO;
		
		LOG.debug("Initial edges: {}", edges);
		
		// Traverse the structure
		for (Direction dir : getStructure()) {
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
		LOG.debug("Found edges {}", edges);
		
		return edges;
	}
	
	/** Converts this brick into a set of occupied positions. */
	default Set<IntVec> getOccupiedPositions() {
		Set<IntVec> positions = new HashSet<>();
		IntVec current = getPos();
		positions.add(current);
		for (Direction dir : getStructure()) {
			current = current.add(dir);
			positions.add(current);
		}
		return positions;
	}
	
	/** Converts the brick into a 2D-array of offset positions. */
	default IntVec[][] to2DArray() {
		IntVec blockPos = getPos();
		Set<IntVec> positions = getOccupiedPositions();
		IntVec min = positions.stream().reduce(IntVec::min).orElse(IntVec.ZERO);
		IntVec max = positions.stream().reduce(IntVec::max).orElse(IntVec.ZERO);
		IntVec diff = max.sub(min);
		IntVec[][] arr = new IntVec[diff.getY() + 1][diff.getX() + 1];
		
		for (int y = 0; y < diff.getY(); y++) {
			for (int x = 0; x < diff.getX(); x++) {
				arr[y][x] = null;
			}
		}
		
		for (IntVec pos : positions) {
			IntVec relPos = pos.sub(min);
			arr[relPos.getY()][relPos.getX()] = pos.sub(blockPos);
		}
		
		return arr;
	}
	
	/** Traverses the brick to check for containment. */
	default boolean contains(IntVec checkedPos) {
		IntVec current = getPos();
		if (current.equals(checkedPos)) return true;
		
		for (Direction dir : getStructure()) {
			current = current.add(dir);
			if (current.equals(checkedPos)) return true;
		}
		
		return false;
	}
}
