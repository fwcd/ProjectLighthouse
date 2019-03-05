package lighthouse.ui.board.viewmodel.graphics;

public interface SceneShapeVisitor {
	void visitRect(OverlayRect rect);
	
	void visitOval(OverlayOval oval);
	
	void visitFixedCircle(OverlayFixedCircle circle);
	
	void visitImage(OverlayImage image);
}
