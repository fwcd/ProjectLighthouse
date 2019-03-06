package lighthouse.gameapi;

import lighthouse.ui.ObservableStatus;

public class GameInitializationContext {
	private final ObservableStatus status;
	private final SceneInteractionFacade interactionFacade;
	
	public GameInitializationContext(ObservableStatus status, SceneInteractionFacade interactionFacade) {
		this.status = status;
		this.interactionFacade = interactionFacade;
	}
	
	public ObservableStatus getStatus() { return status; }
	
	public SceneInteractionFacade getInteractionFacade() { return interactionFacade; }
}
