package lighthouse.util;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorUtils {
	public static final Color LIGHT_GREEN = new Color(195, 255, 150);
	public static final Color LIGHT_CYAN = new Color(140, 255, 247);
	public static final Color LIGHT_ORANGE = new Color(255, 219, 137);
	public static final Color LIGHT_VIOLET = new Color(223, 173, 255);
	
	private ColorUtils() {}
	
	public static Color randomColor() {
		Random r = ThreadLocalRandom.current();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
}
