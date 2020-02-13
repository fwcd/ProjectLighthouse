package lighthouse.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorUtils {
	public static final Color LIGHT_GREEN = new Color(195, 255, 150);
	public static final Color LIGHT_CYAN = new Color(140, 255, 247);
	public static final Color LIGHT_ORANGE = new Color(255, 219, 137);
	public static final Color LIGHT_VIOLET = new Color(223, 173, 255);
	private static final Map<Color, String> KNOWN_NAMES = new HashMap<>();
	
	static {
		KNOWN_NAMES.put(Color.WHITE, "WHITE");
		KNOWN_NAMES.put(Color.LIGHT_GRAY, "LIGHT_GRAY");
		KNOWN_NAMES.put(Color.GRAY, "GRAY");
		KNOWN_NAMES.put(Color.DARK_GRAY, "DARK_GRAY");
		KNOWN_NAMES.put(Color.BLACK, "BLACK");
		KNOWN_NAMES.put(Color.RED, "RED");
		KNOWN_NAMES.put(Color.PINK, "PINK");
		KNOWN_NAMES.put(Color.ORANGE, "ORANGE");
		KNOWN_NAMES.put(Color.YELLOW, "YELLOW");
		KNOWN_NAMES.put(Color.GREEN, "GREEN");
		KNOWN_NAMES.put(Color.MAGENTA, "MAGENTA");
		KNOWN_NAMES.put(Color.CYAN, "CYAN");
		KNOWN_NAMES.put(Color.BLUE, "BLUE");
		KNOWN_NAMES.put(LIGHT_GREEN, "LIGHT_GREEN");
		KNOWN_NAMES.put(LIGHT_CYAN, "LIGHT_CYAN");
		KNOWN_NAMES.put(LIGHT_ORANGE, "LIGHT_ORANGE");
		KNOWN_NAMES.put(LIGHT_VIOLET, "LIGHT_VIOLET");
	}
	
	private ColorUtils() {}
	
	public static Color blend(Color a, Color b) {
		// Source: http://www.java2s.com/Code/Java/2D-Graphics-GUI/Blendtwocolors.htm
		double totalAlpha = a.getAlpha() + b.getAlpha();
		double weightA = a.getAlpha() / totalAlpha;
		double weightB = b.getAlpha() / totalAlpha;
		return new Color(
			(int) (weightA * a.getRed() + weightB * b.getRed()),
			(int) (weightA * a.getGreen() + weightB * b.getGreen()),
			(int) (weightA * a.getBlue() + weightB * b.getBlue())
		);
	}
	
	public static Color overlay(Color bg, Color fg) {
		double fgAlpha = fg.getAlpha() / 255.0;
		double bgAlpha = 1.0 - fgAlpha;
		return new Color(
			Math.max(0, Math.min(255, (int) (fg.getRed() * fgAlpha + bg.getRed() * bgAlpha))),
			Math.max(0, Math.min(255, (int) (fg.getGreen() * fgAlpha + bg.getGreen() * bgAlpha))),
			Math.max(0, Math.min(255, (int) (fg.getBlue() * fgAlpha + bg.getBlue() * bgAlpha)))
		);
	}
	
	public static Color randomColor() {
		Random r = ThreadLocalRandom.current();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	
	public static Color withAlpha(int newAlpha, Color color) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), newAlpha);
	}
	
	public static Color withAlphaPercent(double newAlpha, Color color) {
		return withAlpha((int) (255 * newAlpha), color);
	}
	
	public static int getBrightness(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	public static double getBrightnessPercent(Color color) {
		return getBrightness(color) / 255.0;
	}
	
	public static String describe(Color color) {
		String closestName = "Unknown color";
		int closestDistance = Integer.MAX_VALUE;
		
		for (Color knownColor : KNOWN_NAMES.keySet()) {
			int distance = distance(color, knownColor);
			if (distance == 0) {
				return KNOWN_NAMES.get(knownColor);
			} else if (distance < closestDistance) {
				closestName = KNOWN_NAMES.get(knownColor) + "-ish";
				closestDistance = distance;
			}
		}
		
		return closestName;
	}
	
	/** Computes the squared euclidean distance between a and b in RGB space. */
	public static int distance(Color a, Color b) {
		return MathUtils.square(a.getRed() - b.getRed())
			 + MathUtils.square(a.getGreen() - b.getGreen())
			 + MathUtils.square(a.getBlue() - b.getBlue());
	}
}
