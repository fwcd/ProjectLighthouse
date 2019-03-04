package lighthouse.ui.board.viewmodel.overlay;

public interface OverlayShapeVisitor {
	void visitRect(OverlayRect rect);
	
	void visitOval(OverlayOval oval);
	
	void visitFixedCircle(OverlayFixedCircle circle);
	
	void visitImage(OverlayImage image);
}
