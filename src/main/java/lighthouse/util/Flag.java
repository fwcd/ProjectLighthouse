package lighthouse.util;

/**
 * A mutable boolean.
 */
public class Flag {
	private boolean value = false;
	
	public Flag() { this(false); }
	
	public Flag(boolean value) { this.value = value; }
	
	public boolean isTrue() { return value; }
	
	public boolean isFalse() { return !value; }
	
	public void set(boolean value) { this.value = value; }
}
