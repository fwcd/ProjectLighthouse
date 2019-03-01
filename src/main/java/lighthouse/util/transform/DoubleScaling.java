package lighthouse.util.transform;

import lighthouse.util.DoubleVec;

/**
 * Represents a proportionally scaling DoubleVec-transformation.
 */
public class DoubleScaling implements Bijection<DoubleVec, DoubleVec> {
	private double xFactor;
	private double yFactor;
	private double inverseXFactor;
	private double inverseYFactor;
	
	public DoubleScaling(double xFactor, double yFactor) {
		this.xFactor = xFactor;
		this.yFactor = yFactor;
		inverseXFactor = 1.0 / xFactor;
		inverseYFactor = 1.0 / yFactor;
	}
	
	@Override
	public DoubleVec inverse(DoubleVec pixelCoordinate) {
		return pixelCoordinate.scale(inverseXFactor, inverseYFactor);
	}
	
	@Override
	public DoubleVec apply(DoubleVec gridPos) {
		return gridPos.scale(xFactor, yFactor);
	}
	
	public IntScaling toIntScaling() { return new IntScaling(xFactor, yFactor); }
}
