package lighthouse.ui.board.viewmodel.overlay;

import lighthouse.ui.util.BufferedAnimatedImage;
import lighthouse.ui.util.ResourceImageLoader;
import lighthouse.util.DoubleVec;

public class AnimatedResourceGIFAnimation extends AnimatedImageAnimation {
	public AnimatedResourceGIFAnimation(String resourcePath) {
		super(load(resourcePath));
	}
	
	public AnimatedResourceGIFAnimation(String resourcePath, DoubleVec topLeft) {
		super(load(resourcePath), topLeft);
	}
	
	public AnimatedResourceGIFAnimation(String resourcePath, DoubleVec topLeft, DoubleVec size) {
		super(load(resourcePath), topLeft, size);
	}
	
	private static BufferedAnimatedImage load(String resourcePath) {
		return ResourceImageLoader.getInstance().getAsAnimatedGIF(resourcePath);
	}
}
