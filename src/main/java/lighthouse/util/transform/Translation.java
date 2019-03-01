package lighthouse.util.transform;

import lighthouse.util.DoubleVec;

/**
 * Represents an DoubleVec-translation.
 */
public class Translation implements DoubleVecBijection {
	private double dx;
	private double dy;
	
	public Translation(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public DoubleVec apply(DoubleVec value) {
		return value.add(dx, dy);
	}
	
	@Override
	public DoubleVec inverseApply(DoubleVec value) {
		return value.sub(dx, dy);
	}
}
