package lighthouse.ui.board.transform;

import lighthouse.util.IntVec;

/**
 * Represents an invertible transformation
 * between grid coordinate space and pixel coordinate
 * space.
 */
public interface CoordinateMapper {
	IntVec toPixelPos(IntVec gridPos);
	
	IntVec toGridPos(IntVec pixelCoordinate);
}
