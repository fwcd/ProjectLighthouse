package lighthouse.ui.scene.viewmodel.graphics;

import java.util.List;

public interface SceneLayer {
	List<SceneShape> getShapes();
	
	default boolean hasNextTransitionFrame() { return false; }
	
	default void nextTransitionFrame() {}
	
	default void acceptForAllShapes(SceneShapeVisitor visitor) {
		for (SceneShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
