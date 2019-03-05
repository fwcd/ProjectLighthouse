package lighthouse.ui.scene.viewmodel.graphics;

import java.util.List;

public interface SceneLayer {
	List<SceneShape> getShapes();
	
	default void acceptForAllShapes(SceneShapeVisitor visitor) {
		for (SceneShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
