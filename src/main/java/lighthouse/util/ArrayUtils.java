package lighthouse.util;

/**
 * Utility class featuring methods useful when dealing
 * with arrays.
 */
public class ArrayUtils {
	private ArrayUtils() {}
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static boolean getOr(boolean defaultValue, boolean[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static int getOr(int defaultValue, int[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static char getOr(char defaultValue, char[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static long getOr(long defaultValue, long[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static double getOr(double defaultValue, double[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static float getOr(float defaultValue, float[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static byte getOr(byte defaultValue, byte[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static short getOr(short defaultValue, short[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value if the index is out of bounds */
	public static <T> T getOr(T defaultValue, T[] arr, int i) { return (i >= 0 && i < arr.length) ? arr[i] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static boolean getOr(boolean defaultValue, boolean[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static int getOr(int defaultValue, int[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static char getOr(char defaultValue, char[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static long getOr(long defaultValue, long[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static double getOr(double defaultValue, double[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static float getOr(float defaultValue, float[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static byte getOr(byte defaultValue, byte[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static short getOr(short defaultValue, short[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
	
	/** Fetches an element or a default value from a 2D-array if the indices are out of bounds */
	public static <T> T getOr(T defaultValue, T[][] arr, int y, int x) { return (y >= 0 && y < arr.length && x >= 0 && x < arr[y].length) ? arr[y][x] : defaultValue; }
}
