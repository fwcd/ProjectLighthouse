package lighthouse.util;

public class MathUtils {
	private MathUtils() {}
	
	public static int signum(int x) {
		return (x > 0) ? 1 : ((x < 0) ? -1 : 0);
	}
}
