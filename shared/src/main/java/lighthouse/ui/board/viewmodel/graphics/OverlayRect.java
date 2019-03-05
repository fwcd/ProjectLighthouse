package lighthouse.ui.board.viewmodel.graphics;

import java.awt.Color;

import lighthouse.util.DoubleVec;

public class OverlayRect implements SceneShape {
	private final DoubleVec topLeft;
	private final DoubleVec size;
	private final Color color;
	private final OverlayShading shading;
	
	public OverlayRect(double x, double y, double width, double height, Color color, OverlayShading shading) {
		this(new DoubleVec(x, y), width, height, color, shading);
	}
	
	public OverlayRect(DoubleVec topLeft, double width, double height, Color color, OverlayShading shading) {
		this(topLeft, new DoubleVec(width, height), color, shading);
	}
	
	public OverlayRect(DoubleVec topLeft, DoubleVec size, Color color, OverlayShading shading) {
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
	
	public OverlayShading getShading() { return shading; }
	
	public Color getColor() { return color; }
	
	public double getWidth() { return size.getX(); }
	
	public double getHeight() { return size.getY(); }
	
	public DoubleVec getSize() { return size; }
	
	@Override
	public String toString() {
		return "OverlayRect [topLeft=" + topLeft + ", size=" + size + ", color=" + color + ", shading=" + shading + "]";
	}
}
