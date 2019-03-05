package lighthouse.ui;

import lighthouse.gameapi.Game;
import lighthouse.ui.util.Status;
import lighthouse.util.ListenerList;

/**
 * Holds additional information about the
 * UI state of the application/game.
 */
public class AppContext {
	private final ObservableStatus status = new ObservableStatus();
	private Game activeGame = null;
	
	public Game getActiveGame() { return activeGame; }
	
	public void setActiveGame(Game activeGame) {
		this.activeGame = activeGame;
	}
	
	public ObservableStatus getObservableStatus() { return status; }
	
	public Status getStatus() { return status.get(); }
	
	public void setStatus(Status status) { this.status.set(status); }
	
	public ListenerList<Status> getStatusListeners() { return status.getListeners(); }
}
