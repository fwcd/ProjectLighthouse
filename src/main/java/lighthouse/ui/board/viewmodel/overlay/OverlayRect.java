package lighthouse.ui.board.viewmodel.overlay;

import lighthouse.util.IntVec;

public class OverlayRect implements OverlayShape {
	private final IntVec topLeft;
	private final int width;
	private final int height;
	
	public OverlayRect(int x, int y, int width, int height) {
		this(new IntVec(x, y), width, height);
	}
	
	public OverlayRect(IntVec topLeft, IntVec size) {
		this(topLeft, size.getX(), size.getY());
	}
	
	public OverlayRect(IntVec topLeft, int width, int height) {
		this.topLeft = topLeft;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void accept(OverlayShapeVisitor visitor) {
		visitor.visitRect(this);
	}
	
	public IntVec getTopLeft() { return topLeft; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public IntVec getSize() { return new IntVec(width, height); }
}
