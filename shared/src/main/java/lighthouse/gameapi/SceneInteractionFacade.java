package lighthouse.gameapi;

import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.viewmodel.graphics.Animation;

public interface SceneInteractionFacade {
	void play(Animation animation);
	
	void setResponder(SceneResponder responder);
	
	void update();
	
	void reset();
}
