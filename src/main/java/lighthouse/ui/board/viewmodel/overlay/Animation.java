package lighthouse.ui.board.viewmodel.overlay;

import java.util.List;

public interface Animation {
	int getTotalFrames();
	
	List<OverlayShape> getShape(int frame);
}
