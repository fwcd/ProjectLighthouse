package lighthouse.ui;

import lighthouse.gameapi.Game;
import lighthouse.ui.util.Status;
import lighthouse.util.ListenerList;

/**
 * Holds additional information about the
 * UI state of the application/game.
 */
public class AppContext {
	private Game activeGame = null;
	private Status status = null;
	private final ListenerList<Status> statusListeners = new ListenerList<>("GameContext.statusListeners");
	
	public Game getActiveGame() { return activeGame; }
	
	public Status getStatus() { return status; }
	
	public void setActiveGame(Game activeGame) {
		this.activeGame = activeGame;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
