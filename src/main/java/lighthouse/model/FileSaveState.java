package lighthouse.model;

import java.nio.file.Path;

/**
 * Holds information about whether (and if so, where) the file is saved.
 */
public class FileSaveState {
	private Path saveDestination = null;
	
	public Path getSaveDestination() { return saveDestination; }
	
	public void setSaveDestination(Path saveDestination) { this.saveDestination = saveDestination; }
}
