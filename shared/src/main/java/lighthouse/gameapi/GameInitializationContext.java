package lighthouse.gameapi;

import lighthouse.ui.ObservableStatus;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.scene.controller.DelegateResponder;

public class GameInitializationContext {
	private final ObservableStatus status;
	private final AnimationRunner animationRunner;
	private final DelegateResponder sceneResponder;
	
	public GameInitializationContext(ObservableStatus status, AnimationRunner animationRunner, DelegateResponder sceneResponder) {
		this.status = status;
		this.animationRunner = animationRunner;
		this.sceneResponder = sceneResponder;
	}
	
	public ObservableStatus getStatus() { return status; }
	
	public AnimationRunner getAnimationRunner() { return animationRunner; }
	
	public DelegateResponder getSceneResponder() { return sceneResponder; }
}
