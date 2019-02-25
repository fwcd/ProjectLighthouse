package lighthouse.ui.board.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.board.controller.BoardResponder;

/**
 * A keyboard grid input implementation.
 */
public class BoardKeyInput extends KeyAdapter implements BoardInput {
	private final List<BoardResponder> responders = new ArrayList<>();
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO
	}
}
