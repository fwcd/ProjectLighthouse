package lighthouse.model;

import java.util.List;

import lighthouse.util.IntVec;

/**
 * Common interface for game elements that
 * are structured using connected arrows
 * and a start position.
 */
public interface GameBlock {
	List<Direction> getStructure();
	
	IntVec getPos();
}
