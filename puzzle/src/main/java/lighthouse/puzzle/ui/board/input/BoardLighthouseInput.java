package lighthouse.puzzle.ui.board.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.ui.scene.input.lighthouseapi.ILighthouseInputListener;

/**
 * A remote lighthouse input listener.
 */
public class BoardLighthouseInput implements ILighthouseInputListener, BoardInput {
	private static final Logger LOG = LoggerFactory.getLogger(BoardLighthouseInput.class);
	private final BoardKeyInput keyDelegate = new BoardKeyInput();
	private final KeyCodeConverter keyboardConverter = new JSKeyToSwingKeyCode();
	private final KeyCodeConverter controllerConverter = new JSGamepadToSwingKeyCode();
	
	@Override
	public void addResponder(BoardResponder responder) {
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
