package lighthouse.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface GameState {
	String getName();
	
	/** Saves a level as JSON to a file. */
	void saveLevelTo(Path path) throws IOException;
	
	/** Loads a level from a JSON file. */
	void loadLevelFrom(InputStream stream) throws IOException;
	
    /** Loads a level from an input stream. */
	default void loadLevelFrom(Path path) throws IOException {
		try (InputStream stream = Files.newInputStream(path)) {
			loadLevelFrom(stream);
		}
	}
}
