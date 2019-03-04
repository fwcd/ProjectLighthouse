package lighthouse.ui.util;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * An in-memory animated image.
 */
public class BufferedAnimatedImage implements Iterable<BufferedImage> {
	private final List<BufferedImage> frames;
	
	public BufferedAnimatedImage(List<BufferedImage> frames) {
		this.frames = frames;
	}
	
	public int getFrameCount() { return frames.size(); }
	
	public BufferedImage getFrame(int index) { return frames.get(index); }
	
	public Stream<BufferedImage> streamFrames() { return frames.stream(); }
	
	@Override
	public Iterator<BufferedImage> iterator() { return frames.iterator(); }
}
