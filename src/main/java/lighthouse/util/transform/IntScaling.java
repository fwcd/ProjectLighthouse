package lighthouse.util.transform;

import lighthouse.util.IntVec;

/**
 * Represents a proportionally scaling DoubleVec-transformation.
 */
public class IntScaling implements Bijection<IntVec, IntVec> {
	private double xFactor;
	private double yFactor;
	private double inverseXFactor;
	private double inverseYFactor;
	
	public IntScaling(double xFactor, double yFactor) {
		this.xFactor = xFactor;
		this.yFactor = yFactor;
		inverseXFactor = 1.0 / xFactor;
		inverseYFactor = 1.0 / yFactor;
	}
	
	@Override
	public IntVec inverse(IntVec pixelCoordinate) {
		return pixelCoordinate.scale(inverseXFactor, inverseYFactor).floor();
	}
	
	@Override
	public IntVec apply(IntVec gridPos) {
		return gridPos.scale(xFactor, yFactor).ceil();
	}
	
	public DoubleScaling toDoubleScaling() { return new DoubleScaling(xFactor, yFactor); }
}
