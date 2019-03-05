package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Color;

import lighthouse.util.DoubleVec;

/**
 * Similar to an {@link SceneOval}, but preserves
 * its circle property under any transformation (this
 * means, the radius might not be accurately reflected
 * under every scaling).
 */
public class SceneFixedCircle implements SceneShape {
	private final DoubleVec center;
	private final double radius;
	private final Color color;
	private final Shading shading;
	
	public SceneFixedCircle(DoubleVec center, double radius, Color color, Shading shading) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.shading = shading;
	}
	
	@Override
	public void accept(SceneShapeVisitor visitor) {
		visitor.visitFixedCircle(this);
	}
	
	public Shading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public DoubleVec getCenter() { return center; }
	
	public double getRadius() { return radius; }
	
	public DoubleVec getTopLeft() { return center.sub(radius, radius); }
	
	public DoubleVec getSize() { return new DoubleVec(radius, radius); }
	
	@Override
	public String toString() {
		return "SceneOval [center=" + center + ", radius=" + radius + ", color=" + color + ", shading=" + shading + "]";
	}
}
