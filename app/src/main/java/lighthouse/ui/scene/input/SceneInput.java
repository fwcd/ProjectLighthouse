package lighthouse.ui.scene.input;

import lighthouse.ui.scene.controller.SceneResponder;

/**
 * A general input-facility that can
 * notify responders about user inputs.
 * 
 * <p>Usually, the implementor will be
 * responsible for transforming the raw
 * events into the higher-level representation
 * that is required by a responder.</p>
 */
public interface SceneInput {
	void addResponder(SceneResponder responder);
}
