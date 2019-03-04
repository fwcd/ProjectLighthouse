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
	
	default boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}
	
	default int getInt(String key) {
		return Integer.parseInt(get(key));
	}
	
	default String getOrDefault(String key, String defaultValue) {
		return has(key) ? get(key) : defaultValue;
	}
}
