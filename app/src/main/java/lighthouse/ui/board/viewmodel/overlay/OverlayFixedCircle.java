package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;

import lighthouse.util.DoubleVec;

/**
 * Similar to an {@link OverlayOval}, but preserves
 * its circle property under any transformation (this
 * means, the radius might not be accurately reflected
 * under every scaling).
 */
public class OverlayFixedCircle implements OverlayShape {
	private final DoubleVec center;
	private final double radius;
	private final Color color;
	private final OverlayShading shading;
	
	public OverlayFixedCircle(DoubleVec center, double radius, Color color, OverlayShading shading) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.shading = shading;
	}
	
	@Override
	public void accept(OverlayShapeVisitor visitor) {
		visitor.visitFixedCircle(this);
	}
	
	public OverlayShading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public DoubleVec getCenter() { return center; }
	
	public double getRadius() { return radius; }
	
	public DoubleVec getTopLeft() { return center.sub(radius, radius); }
	
	public DoubleVec getSize() { return new DoubleVec(radius, radius); }
	
	@Override
	public String toString() {
		return "OverlayOval [center=" + center + ", radius=" + radius + ", color=" + color + ", shading=" + shading + "]";
	}
}
