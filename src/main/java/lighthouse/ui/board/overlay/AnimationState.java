package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;

import lighthouse.model.grid.WritableColorGrid;

/**
 * A state wrapper for an animation that iterates once through the frames.
 */
public class AnimationState implements Overlay {
	private final Animation animation;
	private int frame = 0;
	
	public AnimationState(Animation animation) {
		this.animation = animation;
	}
	
	public boolean hasFrame() {
		return frame < animation.getTotalFrames();
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
