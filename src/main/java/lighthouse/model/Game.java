package lighthouse.model;

import lighthouse.util.ListenerList;

/**
 * Holds the game state and information
 * about the game's progress (such as
 * the current stage).
 */
public class Game {
	private GamePlayingState state = new GamePlayingState();
	private Status status;
	private LevelStage stage = LevelStages.IN_GAME;
	
	private final ListenerList<LevelStage> stageListeners = new ListenerList<>();
	private final ListenerList<Status> statusListeners = new ListenerList<>();
	
	public GamePlayingState getState() { return state; }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public void switchToStage(LevelStage newStage) {
		if (stage != null && newStage.getIndex() != stage.getIndex()) {
			newStage.transitionFrom(stage, state);
		}
		stage = newStage;
		stageListeners.fire(newStage);
	}
	
	public LevelStage getLevelStage() { return stage; }
	
	public ListenerList<LevelStage> getLevelStageListeners() { return stageListeners; }
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
