package lighthouse.ui.board.viewmodel.overlay;

public interface OverlayShapeVisitor {
	void visitRect(OverlayRect rect);
	
	void visitOval(OverlayOval oval);
}
