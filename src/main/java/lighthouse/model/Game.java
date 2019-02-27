package lighthouse.model;

import lighthouse.util.ListenerList;

/**
 * Holds the game state and information
 * about the game's progress (such as
 * the current stage).
 */
public class Game {
	private GameState state = new GameState();
	private Status status;
	
	private final ListenerList<Status> statusListeners = new ListenerList<>();
	
	public GameState getState() { return state; }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
