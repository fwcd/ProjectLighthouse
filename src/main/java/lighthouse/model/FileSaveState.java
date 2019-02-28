package lighthouse.model;

import java.nio.file.Path;

import lighthouse.util.ListenerList;

/**
 * Holds information about whether (and if so, where) the file is saved.
 */
public class FileSaveState {
	private Path saveDestination = null;
	private final ListenerList<Path> saveDestinationListeners = new ListenerList<>("FileSaveState.saveDestinationListeners");
	
	public Path getSaveDestination() { return saveDestination; }
	
	public void setSaveDestination(Path saveDestination) {
		this.saveDestination = saveDestination;
		saveDestinationListeners.fire(saveDestination);
	}
	
	public ListenerList<Path> getSaveDestinationListeners() { return saveDestinationListeners; }
}
