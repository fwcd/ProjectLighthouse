package lighthouse.ui.board.viewmodel.overlay;

import java.util.Collections;
import java.util.List;

import lighthouse.ui.util.BufferedAnimatedImage;
import lighthouse.util.DoubleVec;

public class AnimatedImageAnimation implements Animation {
	private final BufferedAnimatedImage image;
	private final DoubleVec topLeft;
	private final DoubleVec size;
	private double speed = 1;
	private boolean loops = false;
	
	public AnimatedImageAnimation(BufferedAnimatedImage image) {
		this(image, DoubleVec.ZERO);
	}
	
	public AnimatedImageAnimation(BufferedAnimatedImage image, DoubleVec topLeft) {
		this(image, topLeft, new DoubleVec(1, 1));
	}
	
	public AnimatedImageAnimation(BufferedAnimatedImage image, DoubleVec topLeft, DoubleVec size) {
		this.image = image;
		this.topLeft = topLeft;
		this.size = size;
	}
	
	@Override
	public String getName() { return "AnimatedImage"; }
	
	@Override
	public int getTotalFrames() { return (int) (image.getFrameCount() / speed); }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		return Collections.singletonList(new OverlayImage(topLeft, image.getFrame((int) (frame * speed)), size));
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setLoops(boolean loops) {
		this.loops = loops;
	}
	
	@Override
	public boolean doesLoop() {
		return loops;
	}
}
