package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Color;

import lighthouse.util.DoubleVec;

public class SceneOval implements SceneShape {
	private final DoubleVec center;
	private final DoubleVec radius;
	private final Color color;
	private final Shading shading;
	
	public SceneOval(DoubleVec center, double radius, Color color, Shading shading) {
		this(center, radius, radius, color, shading);
	}
	
	public SceneOval(DoubleVec center, double xRadius, double yRadius, Color color, Shading shading) {
		this(center, new DoubleVec(xRadius, yRadius), color, shading);
	}
	
	public SceneOval(DoubleVec center, DoubleVec radius, Color color, Shading shading) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.shading = shading;
	}
	
	@Override
	public void accept(SceneShapeVisitor visitor) {
		visitor.visitOval(this);
	}
	
	public Shading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public DoubleVec getCenter() { return center; }
	
	public double getXRadius() { return radius.getX(); }
	
	public double getYRadius() { return radius.getY(); }
	
	public DoubleVec getRadius() { return radius; }
	
	public DoubleVec getTopLeft() { return center.sub(radius); }
	
	public DoubleVec getSize() { return radius.scale(2); }
	
	@Override
	public String toString() {
		return "SceneOval [center=" + center + ", radius=" + radius + ", color=" + color + ", shading=" + shading + "]";
	}
}
