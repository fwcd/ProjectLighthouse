package lighthouse.ui.board.viewmodel.overlay;

import java.util.Collections;
import java.util.List;

import lighthouse.ui.util.BufferedAnimatedImage;
import lighthouse.util.DoubleVec;

public class AnimatedImageAnimation implements Animation {
	private final BufferedAnimatedImage image;
	private final DoubleVec topLeft;
	private final DoubleVec size;
	
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
	public int getTotalFrames() { return image.getFrameCount(); }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		return Collections.singletonList(new OverlayImage(topLeft, image.getFrame(frame), size));
	}
}
