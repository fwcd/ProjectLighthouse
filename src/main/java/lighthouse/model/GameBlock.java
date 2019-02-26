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
