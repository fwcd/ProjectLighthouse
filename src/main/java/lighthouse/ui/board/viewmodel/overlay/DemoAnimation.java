package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class DemoAnimation implements Animation {
	@Override
	public int getTotalFrames() { return 500; }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		return Collections.singletonList(
			new OverlayRect(1, frame, 1, 1, Color.CYAN, OverlayShading.FILLED)
		);
	}
}
