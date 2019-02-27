package lighthouse.model;

import lighthouse.ui.loop.Ticker;
import lighthouse.util.ListenerList;

/**
 * Holds the game state and information
 * about the game's progress (such as
 * the current stage).
 */
public class Game implements Ticker {
	private GameState state = new GameState();
	private Status status;
	
	private final ListenerList<Status> statusListeners = new ListenerList<>();

	private boolean won = false;

	@Override
	public void tick() {
		// if (state.getLevel().isCompleted(state.getBoard())) {
		// 	LOG.info("Completed level");
		// 	won = true;
		// }
	}
	
	public GameState getState() { return state; }
	
	public boolean isWon() { return won; }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
