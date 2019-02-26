package lighthouse.util;

/**
 * Utility class featuring methods useful when dealing
 * with arrays.
 */
public class ArrayUtils {
	private ArrayUtils() {}
	
	public static boolean getOr(boolean[] arr, int i, boolean defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static int getOr(int[] arr, int i, int defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static char getOr(char[] arr, int i, char defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static long getOr(long[] arr, int i, long defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static double getOr(double[] arr, int i, double defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static float getOr(float[] arr, int i, float defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static byte getOr(byte[] arr, int i, byte defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static short getOr(short[] arr, int i, short defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	public static <T> T getOr(T[] arr, int i, T defaultValue) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
}
