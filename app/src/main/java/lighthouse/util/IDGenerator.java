package lighthouse.util;

/**
 * A global ID generator.
 */
public class IDGenerator {
	public static IDGenerator INSTANCE = new IDGenerator();
	private int currentID = 0;
	
	private IDGenerator() {}
	
	public synchronized int nextID() { return currentID++; }
}
