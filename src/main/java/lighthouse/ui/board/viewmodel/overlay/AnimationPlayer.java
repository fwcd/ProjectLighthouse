package lighthouse.ui.board.viewmodel.overlay;

import java.util.List;

public class AnimationPlayer implements Overlay {
	private final Animation animation;
	private int frame = 0;
	
	public AnimationPlayer(Animation animation) {
		this.animation = animation;
	}
	
	@Override
	public List<OverlayShape> getShapes() {
		return animation.getShape(frame);
	}
	
	public boolean hasNextFrame() {
		return frame < (animation.getTotalFrames() - 1);
	}
	
	public void nextFrame() {
		frame++;
	}
}
