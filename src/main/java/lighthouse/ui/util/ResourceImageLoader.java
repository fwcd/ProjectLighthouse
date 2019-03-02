package lighthouse.ui.util;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceImageLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceImageLoader.class);
	private static final ResourceImageLoader INSTANCE = new ResourceImageLoader();
	private final Map<String, BufferedImage> loadedImages = new ConcurrentHashMap<>();
	
	public static ResourceImageLoader getInstance() { return INSTANCE; }
}
