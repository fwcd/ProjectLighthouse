package lighthouse.ui.scene.viewmodel.graphics;

import java.util.List;

public interface Scene {
	List<SceneShape> getShapes();
	
	default void acceptForAllShapes(SceneShapeVisitor visitor) {
		for (SceneShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
