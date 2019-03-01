package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import lighthouse.util.ColorUtils;
import lighthouse.util.DoubleVec;

public class DemoAnimation implements Animation {
	@Override
	public String getName() { return "Demo animation"; }
	
	@Override
	public int getTotalFrames() { return 300; }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		return Arrays.asList(
			new OverlayRect(1, frame / 40.0, 1, 1, Color.CYAN, OverlayShading.FILLED),
			new OverlayOval(new DoubleVec(2, 3), frame / 40.0, ColorUtils.withAlpha(((300 - frame) * 255) / 300, ColorUtils.LIGHT_VIOLET), OverlayShading.FILLED)
		);
	}
}
