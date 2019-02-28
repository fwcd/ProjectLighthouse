package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;
import java.util.List;

import lighthouse.model.grid.WritableColorGrid;

public class AnimationPlayer implements Overlay {
	private List<AnimationState> states;
	
	@Override
	public void drawHighRes(Graphics2D g2d) {
		// TODO
	}
	
	@Override
	public void drawLowRes(WritableColorGrid grid) {
		// TODO
	}
	
	private static class AnimationState {
		private int frame = 0;
		private Animation animation;
	}
}
