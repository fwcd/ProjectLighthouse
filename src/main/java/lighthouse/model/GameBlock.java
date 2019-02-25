package lighthouse.model;

import java.awt.Color;
import java.util.List;

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
}
