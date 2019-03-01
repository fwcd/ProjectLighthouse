package lighthouse.ui.board.viewmodel.overlay;

/**
 * A drawable overlay item. Coordinates
 * are in grid space, but with double precision.
 */
public interface OverlayShape {
	void accept(OverlayShapeVisitor visitor);
}
