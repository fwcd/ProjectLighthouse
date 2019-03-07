package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Color;

import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntRect;

public class SceneRect implements SceneShape {
	private final DoubleVec topLeft;
	private final DoubleVec size;
	private final Color color;
	private final Shading shading;
	
	public SceneRect(IntRect rect, Color color, Shading shading) {
		this(rect.toDouble(), color, shading);
	}
	
	public SceneRect(DoubleRect rect, Color color, Shading shading) {
		this(rect.getTopLeft(), rect.getSize(), color, shading);
	}
	
	public SceneRect(double x, double y, double width, double height, Color color, Shading shading) {
		this(new DoubleVec(x, y), width, height, color, shading);
	}
	
	public SceneRect(DoubleVec topLeft, double width, double height, Color color, Shading shading) {
		this(topLeft, new DoubleVec(width, height), color, shading);
	}
	
	public SceneRect(DoubleVec topLeft, DoubleVec size, Color color, Shading shading) {
		this.topLeft = topLeft;
		this.size = size;
		this.color = color;
		this.shading = shading;
	}
	
	@Override
	public void accept(SceneShapeVisitor visitor) {
		visitor.visitRect(this);
	}
	
	public DoubleVec getTopLeft() { return topLeft; }
	
	public Shading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public double getWidth() { return size.getX(); }
	
	public double getHeight() { return size.getY(); }
	
	public DoubleVec getSize() { return size; }
	
	@Override
	public String toString() {
		return "SceneRect [topLeft=" + topLeft + ", size=" + size + ", color=" + color + ", shading=" + shading + "]";
	}
}
