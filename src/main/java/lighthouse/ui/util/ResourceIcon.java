package lighthouse.ui.util;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An icon located in {@code src/main/resources}.
 */
public class ResourceIcon {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceIcon.class);
	private ImageIcon icon;
	
	public ResourceIcon(String resource) {
		try (InputStream stream = ResourceIcon.class.getResourceAsStream(resource)) {
			icon = new ImageIcon(ImageIO.read(stream));
		} catch (IOException e) {
			LOG.warn("An error occurred while loading a resource icon:", e);
		}
	}
	
	public ImageIcon get() { return icon; }
}
