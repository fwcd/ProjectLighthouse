package lighthouse.gameapi;

import lighthouse.ui.ObservableStatus;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.util.Updatable;

public class GameInitializationContext {
	private final ObservableStatus status;
	private final AnimationRunner animationRunner;
	private final DelegateResponder sceneResponder;
	private final Updatable updater;
	
	public GameInitializationContext(ObservableStatus status, AnimationRunner animationRunner, DelegateResponder sceneResponder, Updatable updater) {
		this.status = status;
		this.animationRunner = animationRunner;
		this.sceneResponder = sceneResponder;
		this.updater = updater;
	}
	
	public ObservableStatus getStatus() { return status; }
	
	public AnimationRunner getAnimationRunner() { return animationRunner; }
	
	public DelegateResponder getSceneResponder() { return sceneResponder; }
	
	public Updatable getUpdater() { return updater; }
}
