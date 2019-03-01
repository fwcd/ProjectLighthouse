package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;

import lighthouse.util.DoubleVec;

public class OverlayOval implements OverlayShape {
	private final DoubleVec center;
	private final DoubleVec radius;
	private final Color color;
	private final OverlayShading shading;
	
	public OverlayOval(DoubleVec center, double radius, Color color, OverlayShading shading) {
		this(center, radius, radius, color, shading);
	}
	
	public OverlayOval(DoubleVec center, double xRadius, double yRadius, Color color, OverlayShading shading) {
		this(center, new DoubleVec(xRadius, yRadius), color, shading);
	}
	
	public OverlayOval(DoubleVec center, DoubleVec radius, Color color, OverlayShading shading) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.shading = shading;
	}
	
	@Override
	public void accept(OverlayShapeVisitor visitor) {
		visitor.visitOval(this);
	}
	
	public OverlayShading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public DoubleVec getCenter() { return center; }
	
	public double getXRadius() { return radius.getX(); }
	
	public double getYRadius() { return radius.getY(); }
	
	public DoubleVec getRadius() { return radius; }
	
	public DoubleVec getTopLeft() { return center.sub(radius); }
	
	public DoubleVec getSize() { return radius.scale(2); }
}
