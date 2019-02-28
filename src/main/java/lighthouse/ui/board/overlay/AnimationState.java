package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

/**
 * A state wrapper that iterates through the
 * frames of an animation.
 */
public class AnimationState implements Overlay {
	private final Animation animation;
	private int frame = 0;
	
	public AnimationState(Animation animation) {
		this.animation = animation;
	}
	
	public boolean hasNextFrame() {
		return frame < (animation.getTotalFrames() - 1);
	}
	
	@Override
	public void drawHighRes(Graphics2D g2d) {
		animation.drawHighRes(g2d, frame);
	}
	
	@Override
	public void drawLowRes(WritableColorGrid grid) {
		animation.drawLowRes(grid, frame);
	}
	
	public void advance() {
		frame++;
	}
}
