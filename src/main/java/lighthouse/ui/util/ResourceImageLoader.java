package lighthouse.ui.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cache for resource images.
 */
public class ResourceImageLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceImageLoader.class);
	private static final ResourceImageLoader INSTANCE = new ResourceImageLoader();
	private final Map<String, BufferedImage> loadedBufferedImages = new ConcurrentHashMap<>();
	
	public static ResourceImageLoader getInstance() { return INSTANCE; }
	
	private BufferedImage loadFrom(String resourcePath) {
		try (InputStream stream = ResourceImageLoader.class.getResourceAsStream(resourcePath)) {
			return ImageIO.read(stream);
		} catch (IOException e) {
			LOG.error("Error while loading image resource:", e);
			return null;
		}
	}
	
	public BufferedImage get(String resourcePath) {
		BufferedImage loaded = loadedBufferedImages.get(resourcePath);
		if (loaded == null) {
			loaded = loadFrom(resourcePath);
			if (loaded != null) {
				loadedBufferedImages.put(resourcePath, loaded);
			}
		}
		return loaded;
	}
	
	public ImageIcon getAsIcon(String resourcePath) {
		return new ImageIcon(resourcePath);
	}
}
