package lighthouse.ui.board;

import lighthouse.util.IntVec;

public class ScaleTransform implements CoordinateMapper {
	private final double xFactor;
	private final double yFactor;
	private final double inverseXFactor;
	private final double inverseYFactor;
	
	public ScaleTransform(double xFactor, double yFactor) {
		this.xFactor = xFactor;
		this.yFactor = yFactor;
		inverseXFactor = 1.0 / xFactor;
		inverseYFactor = 1.0 / yFactor;
	}
	
	@Override
	public IntVec toGridPos(IntVec pixelCoordinate) {
		return pixelCoordinate.scale(inverseXFactor, inverseYFactor);
	}
	
	@Override
	public IntVec toPixelCoordinate(IntVec gridPos) {
		return gridPos.scale(xFactor, yFactor);
	}
}
