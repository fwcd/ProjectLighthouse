package lighthouse.ui.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class AnimatedGIFLoader {
	private static final String FORMAT_NAME = "gif";
	
	public BufferedAnimatedImage loadGIFFrom(InputStream stream) throws IOException {
		try (ImageInputStream imageIn = ImageIO.createImageInputStream(stream)) {
			ImageReader reader = ImageIO.getImageReadersByFormatName(FORMAT_NAME).next();
			reader.setInput(imageIn, false);
			
			int imageCount = reader.getNumImages(true);
			List<BufferedImage> frames = new ArrayList<>(imageCount);
			
			for (int i = 0; i < imageCount; i++) {
				frames.add(reader.read(i));
			}
			
			return new BufferedAnimatedImage(frames);
		}
	}
}
