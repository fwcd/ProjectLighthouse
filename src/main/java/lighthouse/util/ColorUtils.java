package lighthouse.util;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorUtils {
	public static Color randomColor() {
		Random r = ThreadLocalRandom.current();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
}
