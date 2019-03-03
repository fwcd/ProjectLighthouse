package lighthouse.ui.board.input;

import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.view.lighthouseapi.ILighthouseInputListener;

/**
 * A remote lighthouse input listener.
 */
public class BoardLighthouseInput implements ILighthouseInputListener, BoardInput {
	private final BoardKeyInput keyDelegate = new BoardKeyInput();
	private final KeyCodeConverter converter = new JavaScriptToSwingKeyCode();
	
	@Override
	public void addResponder(BoardResponder responder) {
		keyDelegate.addResponder(responder);
	}
	
	@Override
	public void controllerEvent(int source, int button, boolean down) {
		// TODO
	}
	
	@Override
	public void keyboardEvent(int source, int button, boolean down) {
		int keyCode = converter.convert(button);
		if (down) {
			keyDelegate.keyPressed(keyCode);
		} else {
			keyDelegate.keyReleased(keyCode);
		}
	}
}
