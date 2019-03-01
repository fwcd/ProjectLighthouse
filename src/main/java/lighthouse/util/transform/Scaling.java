package lighthouse.util.transform;

import lighthouse.util.IntVec;

/**
 * Represents a proportionally scaling IntVec-transformation.
 */
public class Scaling implements Bijection<IntVec> {
	private double xFactor;
	private double yFactor;
	private double inverseXFactor;
	private double inverseYFactor;
	
	public Scaling(double xFactor, double yFactor) {
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
	public IntVec inverse(IntVec pixelCoordinate) {
		return pixelCoordinate.scale(inverseXFactor, inverseYFactor).castToInt();
	}
	
	@Override
	public IntVec apply(IntVec gridPos) {
		return gridPos.scale(xFactor, yFactor).castToInt();
	}
}
