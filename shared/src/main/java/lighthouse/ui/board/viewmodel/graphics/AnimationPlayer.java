package lighthouse.ui.board.viewmodel.graphics;

import java.util.List;

public class AnimationPlayer implements Overlay {
	private final Animation animation;
	private int frame = 0;
	
	public AnimationPlayer(Animation animation) {
		this.animation = animation;
	}
	
	@Override
	public List<SceneShape> getShapes() {
		return animation.getShape(frame % animation.getTotalFrames());
	}
	
	public boolean hasNextFrame() {
		return frame < (animation.getTotalLoopedFrames() - 1);
	}
	
	public int getCurrentFrame() {
		return frame;
	}
	
	public void nextFrame() {
		frame = (frame + 1);
	}
}
