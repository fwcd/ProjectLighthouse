package lighthouse.ui.board.viewmodel.graphics;

import java.util.List;

public interface Overlay {
	List<SceneShape> getShapes();
	
	default void acceptForAllShapes(SceneShapeVisitor visitor) {
		for (SceneShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
