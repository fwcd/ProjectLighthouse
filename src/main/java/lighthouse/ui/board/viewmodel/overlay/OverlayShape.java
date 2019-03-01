package lighthouse.ui.board.viewmodel.overlay;

/**
 * A drawable overlay item.
 */
public interface OverlayShape {
	void accept(OverlayShapeVisitor visitor);
}
