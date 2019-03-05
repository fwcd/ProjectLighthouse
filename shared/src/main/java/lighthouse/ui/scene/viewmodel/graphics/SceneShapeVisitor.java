package lighthouse.ui.scene.viewmodel.graphics;

public interface SceneShapeVisitor {
	void visitRect(SceneRect rect);
	
	void visitOval(SceneOval oval);
	
	void visitFixedCircle(SceneFixedCircle circle);
	
	void visitImage(SceneImage image);
}
