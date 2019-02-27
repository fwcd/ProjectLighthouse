package lighthouse.ui;

import lighthouse.ui.util.Status;
import lighthouse.util.ListenerList;

/**
 * Holds additional information about the
 * UI state of the game.
 */
public class GameContext {
	private Status status;
	private final ListenerList<Status> statusListeners = new ListenerList<>();
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
