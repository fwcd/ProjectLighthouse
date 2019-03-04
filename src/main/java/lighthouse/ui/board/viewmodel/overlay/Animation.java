package lighthouse.ui.board.viewmodel.overlay;

import java.util.List;

public interface Animation {
	String getName();
	
	int getTotalFrames();
	
	List<OverlayShape> getShape(int frame);
	
	default boolean doesLoop() { return false; }
}
