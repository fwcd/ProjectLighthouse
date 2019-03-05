package lighthouse.ui.board.viewmodel.overlay;

import java.util.List;

public interface Overlay {
	List<OverlayShape> getShapes();
	
	default void acceptForAllShapes(OverlayShapeVisitor visitor) {
		for (OverlayShape shape : getShapes()) {
			shape.accept(visitor);
		}
	}
}
