package lighthouse.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lighthouse.util.IntVec;

/**
 * Common interface for colored game elements that
 * are structured using connected arrows
 * and a start position.
 */
public interface GameBlock {
	List<? extends Direction> getStructure();
	
	IntVec getPos();
	
	Color getColor();
	
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
	
	/** Converts the brick into a boolean array. */
	default boolean[][] toBoolArray() {
		Set<IntVec> positions = getOccupiedPositions();
		IntVec min = positions.stream().reduce(IntVec::min).orElse(IntVec.ZERO);
		IntVec max = positions.stream().reduce(IntVec::max).orElse(IntVec.ZERO);
		IntVec diff = max.sub(min);
		boolean[][] arr = new boolean[diff.getY()][diff.getX()];
		
		for (int y = 0; y < diff.getY(); y++) {
			for (int x = 0; x < diff.getX(); x++) {
				arr[y][x] = false;
			}
		}
		
		for (IntVec pos : positions) {
			arr[pos.getY()][pos.getX()] = true;
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
