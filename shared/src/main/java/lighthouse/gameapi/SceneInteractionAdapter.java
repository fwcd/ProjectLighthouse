package lighthouse.gameapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.viewmodel.graphics.Animation;

public class SceneInteractionAdapter implements SceneInteractionFacade {
	private static final Logger LOG = LoggerFactory.getLogger(SceneInteractionAdapter.class);
	
	@Override
	public void play(Animation animation) {
		LOG.debug("Did not implement play(Animation)");
	}
	
	@Override
	public void reset() {
		LOG.debug("Did not implement reset()");
	}
	
	@Override
	public void setResponder(SceneResponder responder) {
		LOG.debug("Did not implement setResponder(SceneResponder)");
	}
	
	@Override
	public void update() {
		LOG.debug("Did not implement update()");
	}
}
