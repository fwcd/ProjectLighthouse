package lighthouse.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.loop.Ticker;
import lighthouse.util.ListenerList;

/**
 * Holds the game state and information
 * about the game's progress (such as
 * the current stage).
 */
public class Game implements Ticker {
	private static final Logger LOG = LoggerFactory.getLogger(Game.class);
	private GameState state = new GameState();
	private GameStage stage = GameStages.CURRENT;
	private Status status;
	
	private final ListenerList<GameStage> stageListeners = new ListenerList<>();
	private final ListenerList<Status> statusListeners = new ListenerList<>();

	private boolean won = false;
	
	public Game() {
		setupListeners();
	}

	@Override
	public void tick() {
		// if (state.getLevel().isCompleted(state.getBoard())) {
		// 	LOG.info("Completed level");
		// 	won = true;
		// }
	}
	
	private void setupListeners() {
		state.getLevelListeners().add(level -> {
			// Update stage if the level has changed
			stage.transitionFrom(stage, state);
		});
	}
	
	public GameState getState() { return state; }
	
	public GameStage getCurrentStage() { return stage; }
	
	public void switchToStage(GameStage newStage) {
		if (stage != null && newStage.getIndex() != stage.getIndex()) {
			newStage.transitionFrom(stage, state);
		}
		stage = newStage;
		stageListeners.fire(newStage);
	}
	
	public boolean isWon() { return won; }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
	
	public ListenerList<GameStage> getStageListeners() { return stageListeners; }
}
