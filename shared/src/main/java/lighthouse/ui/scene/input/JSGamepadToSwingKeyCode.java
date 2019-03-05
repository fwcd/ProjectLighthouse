package lighthouse.ui.scene.input;

import java.awt.event.KeyEvent;

public class JSGamepadToSwingKeyCode implements KeyCodeConverter {
	private int upCode = 4;
	private int leftCode = 3;
	private int rightCode = 1;
	private int downCode = 0;
	private int enterCode = 9;
	
	@Override
	public int convert(int keyCode) {
		if (keyCode == upCode) {
			return KeyEvent.VK_UP;
		} else if (keyCode == downCode) {
			return KeyEvent.VK_DOWN;
		} else if (keyCode == leftCode) {
			return KeyEvent.VK_LEFT;
		} else if (keyCode == rightCode) {
			return KeyEvent.VK_RIGHT;
		} else if (keyCode == enterCode) {
			return KeyEvent.VK_ENTER;
		} else {
			return KeyEvent.VK_UNDEFINED;
		}
	}
}
