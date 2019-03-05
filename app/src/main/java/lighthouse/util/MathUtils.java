package lighthouse.util;

public class MathUtils {
	private static final SinTable SIN_TABLE = new SinTable();
	
	private MathUtils() {}
	
	public static int signum(int x) {
		return (x > 0) ? 1 : ((x < 0) ? -1 : 0);
	}
	
	public static int square(int x) {
		return x * x;
	}
	
	public static double square(double x) {
		return x * x;
	}
	
	public static float sinDeg(float degrees) {
		return SIN_TABLE.sinDeg(degrees);
	}
	
	public static float cosDeg(float degrees) {
		return SIN_TABLE.cosDeg(degrees);
	}
}
