package lighthouse.ui.board;

import lighthouse.util.IntVec;

/**
 * Represents a proportionally scaling transformation.
 */
public class ScaleTransform implements CoordinateMapper {
	private double xFactor;
	private double yFactor;
	private double inverseXFactor;
	private double inverseYFactor;
	
	public ScaleTransform(double xFactor, double yFactor) {
		setXFactor(xFactor);
		setYFactor(yFactor);
	}
	
	public void setXFactor(double xFactor) {
		this.xFactor = xFactor;
		inverseXFactor = 1.0 / xFactor;
	}
	
	public void setYFactor(double yFactor) {
		this.yFactor = yFactor;
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
