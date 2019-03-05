package lighthouse.ui.scene.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.input.lighthouseapi.ILighthouseInputListener;

/**
 * A remote lighthouse input listener.
 */
public class SceneLighthouseInput implements ILighthouseInputListener, SceneInput {
	private static final Logger LOG = LoggerFactory.getLogger(SceneLighthouseInput.class);
	private final SceneKeyInput keyDelegate = new SceneKeyInput();
	private final KeyCodeConverter keyboardConverter = new JSKeyToSwingKeyCode();
	private final KeyCodeConverter controllerConverter = new JSGamepadToSwingKeyCode();
	
	@Override
	public void addResponder(SceneResponder responder) {
		keyDelegate.addResponder(responder);
	}
	
	@Override
	public void controllerEvent(int source, int button, boolean down) {
		LOG.debug("Got controller event: source = {}, button = {}, down = {}", source, button, down);
		handleEvent(button, down, controllerConverter);
	}
	
	@Override
	public void keyboardEvent(int source, int button, boolean down) {
		LOG.debug("Got keyboard event: source = {}, button = {}, down = {}", source, button, down);
		handleEvent(button, down, keyboardConverter);
	}
	
	private void handleEvent(int button, boolean down, KeyCodeConverter converter) {
		int keyCode = keyboardConverter.convert(button);
		if (down) {
			keyDelegate.keyPressed(keyCode);
		} else {
			keyDelegate.keyReleased(keyCode);
		}
	}
}
