package lighthouse.ui.board;

import lighthouse.util.IntVec;

/**
 * Represents an invertible transformation
 * between grid coordinate space and pixel coordinate
 * space.
 */
public interface CoordinateMapper {
	IntVec toPixelCoordinate(IntVec gridPos);
	
	IntVec toGridPos(IntVec pixelCoordinate);
}
