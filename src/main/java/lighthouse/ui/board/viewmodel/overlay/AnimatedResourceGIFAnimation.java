package lighthouse.ui.board.viewmodel.overlay;

import lighthouse.ui.util.ResourceImageLoader;

public class AnimatedResourceGIFAnimation extends AnimatedImageAnimation {
	public AnimatedResourceGIFAnimation(String resourcePath) {
		super(ResourceImageLoader.getInstance().getAsAnimatedGIF(resourcePath));
	}
}
