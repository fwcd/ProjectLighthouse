package lighthouse.ui.board.input;

import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.view.lighthouseapi.ILighthouseInputListener;

/**
 * A remote lighthouse input listener.
 */
public class BoardLighthouseInput implements ILighthouseInputListener, BoardInput {
	private final List<BoardResponder> responders = new ArrayList<>();
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void controllerEvent(int source, int button, boolean down) {
		// TODO
	}
	
	@Override
	public void keyboardEvent(int source, int button, boolean down) {
		// TODO
	}
}
