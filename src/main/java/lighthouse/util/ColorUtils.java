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
		double fgAlpha = fg.getAlpha();
		double bgAlpha = 1.0 - fgAlpha;
		return new Color(
			(int) (fg.getRed() * fgAlpha + bg.getRed() * bgAlpha),
			(int) (fg.getGreen() * fgAlpha + bg.getGreen() * bgAlpha),
			(int) (fg.getBlue() * fgAlpha + bg.getBlue() * bgAlpha)
		);
	}
	
	public static Color randomColor() {
		Random r = ThreadLocalRandom.current();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	
	public static Color withAlpha(int newAlpha, Color color) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), newAlpha);
	}
	
	public static int getBrightness(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	public static double getBrightnessPercent(Color color) {
		return getBrightness(color) / 255.0;
	}
}
