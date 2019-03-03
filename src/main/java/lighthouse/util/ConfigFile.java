package lighthouse.util;

/**
 * A key-value configuration file. The file format looks like this:
 * 
 * <pre>
 * somekey=somevalue
 * anotherkey=anothervalue
 * </pre>
 */
public interface ConfigFile {
	String get(String key);
	
	boolean has(String key);
	
	void set(String key, String value);
	
	default String getOrDefault(String key, String defaultValue) {
		return has(key) ? get(key) : defaultValue;
	}
}
