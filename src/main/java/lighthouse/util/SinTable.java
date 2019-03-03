package lighthouse.util;

/**
 * A sine-/cosine value-table.
 */
public class SinTable {
	private final int precision; // Values per degree
	private final int mod;
	private final float[] table;
	
	public SinTable() {
		this(100);
	}
	
	public SinTable(int precision) {
		this.precision = precision;
		
		mod = 360 * precision;
		table = new float[mod];
		
		for (int i=0; i<mod; i++) {
			table[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
		}
	}
	
	private float lookup(int i) {
	    return i >= 0
	    		? table[i % mod]
	    		: -table[-i % mod];
	}
	
	public float sinDeg(float degrees) {
		return lookup((int) (degrees * precision + 0.5F));
	}
	
	public float cosDeg(float degrees) {
		return lookup((int) ((degrees + 90.0) * precision + 0.5F));
	}
}
