package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Color;
import java.util.List;

public interface SceneLayer {
	List<SceneShape> getShapes();
	
	default boolean hasBackground() { return false; }
	
	default Color getBackground() { return Color.BLACK; }
	
	default boolean requiresGridBackground() { return false; }
	
	default boolean hasNextTransitionFrame() { return false; }
	
	default void nextTransitionFrame() {}
	
	default void acceptForAllShapes(SceneShapeVisitor visitor) {
		for (SceneShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
