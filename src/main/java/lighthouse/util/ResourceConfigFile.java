package lighthouse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A read-only configuration file located
 * in the {@code src/main/resources} directory.
 * When the project is built, these configurations
 * are bundled directly into the JAR.
 */
public class ResourceConfigFile implements ConfigFile {
	private final Map<String, String> dict;
	
	public ResourceConfigFile(String resourcePath) {
		dict = readFrom(resourcePath);
	}
	
	private Map<String, String> readFrom(String resourcePath) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceConfigFile.class.getResourceAsStream(resourcePath)))) {
			return reader.lines()
				.map(line -> line.split("="))
				.collect(Collectors.toMap(
					splitted -> splitted[0],
					splitted -> splitted[1]
				));
		} catch (IOException e) {
			System.out.println("Warning: Did not find the config file " + resourcePath); // TODO: Find a better logging solution
			return Collections.emptyMap();
		}
	}
	
	@Override
	public boolean has(String key) {
		return dict.containsKey(key);
	}
	
	@Override
	public String get(String key) {
		return dict.get(key);
	}
	
	@Override
	public void set(String key, String value) {
		throw new UnsupportedOperationException("Resource configuration files are read-only");
	}
}
