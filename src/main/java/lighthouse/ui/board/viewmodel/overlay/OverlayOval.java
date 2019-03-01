package lighthouse.ui.board.viewmodel.overlay;

import lighthouse.util.IntVec;

public class OverlayOval implements OverlayShape {
	private final IntVec center;
	private final int xRadius;
	private final int yRadius;
	
	public OverlayOval(IntVec center, int radius) {
		this(center, radius, radius);
	}
	
	public OverlayOval(IntVec center, int xRadius, int yRadius) {
		this.center = center;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
	}
	
	@Override
	public void accept(OverlayShapeVisitor visitor) {
		visitor.visitOval(this);
	}
	
	public IntVec getCenter() { return center; }
	
	public int getXRadius() { return xRadius; }
	
	public int getYRadius() { return yRadius; }
}
