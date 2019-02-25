package lighthouse.ui.board;

import lighthouse.util.IntVec;

public interface CoordinateMapper {
	IntVec toPixelCoordinate(IntVec gridPos);
	
	IntVec toGridPos(IntVec pixelCoordinate);
}
