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
	
	public static boolean getOr(boolean[][] arr, int y, int x, boolean defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static int getOr(int[][] arr, int y, int x, int defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static char getOr(char[][] arr, int y, int x, char defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static long getOr(long[][] arr, int y, int x, long defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static double getOr(double[][] arr, int y, int x, double defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static float getOr(float[][] arr, int y, int x, float defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static byte getOr(byte[][] arr, int y, int x, byte defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static short getOr(short[][] arr, int y, int x, short defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	public static <T> T getOr(T[][] arr, int y, int x, T defaultValue) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
}
