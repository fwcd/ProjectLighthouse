package lighthouse.util.transform;

import lighthouse.util.DoubleVec;

/**
 * Represents a proportionally scaling DoubleVec-transformation.
 */
public class Scaling implements DoubleVecBijection {
	private double xFactor;
	private double yFactor;
	
	public Scaling(double xFactor, double yFactor) {
		this.xFactor = xFactor;
		this.yFactor = yFactor;
	}
	
	@Override
	public DoubleVec inverseApply(DoubleVec pixelCoordinate) {
		return pixelCoordinate.divide(xFactor, yFactor);
	}
	
	@Override
	public DoubleVec apply(DoubleVec gridPos) {
		return gridPos.scale(xFactor, yFactor);
	}
}
