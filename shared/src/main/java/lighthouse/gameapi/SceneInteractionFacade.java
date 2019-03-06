package lighthouse.gameapi;

import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.viewmodel.graphics.Animation;

/**
 * Provides an API for interacting with the scene
 * from a game module.
 */
public interface SceneInteractionFacade {
	/** Plays an animation. */
	void play(Animation animation);
	
	/** Swaps the scene responder. */
	void setResponder(SceneResponder responder);
	
	/** Updates the scene UI. */
	void update();
	
	/** "Resets" the scene in some sense. The precise meaning may depend on the responder. */
	void reset();
}
