package lighthouse.ui.scene;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.viewmodel.graphics.Animation;
import lighthouse.util.Updatable;

public class SceneInteractionBackend implements SceneInteractionFacade {
	private final AnimationRunner animationRunner;
	private final DelegateResponder delegateResponder;
	private final Updatable updater;
	
	public SceneInteractionBackend(AnimationRunner animationRunner, DelegateResponder delegateResponder, Updatable updater) {
		this.animationRunner = animationRunner;
		this.delegateResponder = delegateResponder;
		this.updater = updater;
	}
	
	@Override
	public void play(Animation animation) {
		animationRunner.play(animation);
	}
	
	@Override
	public void setResponder(SceneResponder responder) {
		delegateResponder.setDelegate(responder);
	}
	
	@Override
	public void update() {
		updater.update();
	}
	
	@Override
	public void reset() {
		delegateResponder.reset();
	}
}
