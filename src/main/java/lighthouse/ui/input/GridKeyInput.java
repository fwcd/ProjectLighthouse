package lighthouse.ui.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.controller.GridResponder;

public class GridKeyInput extends KeyAdapter implements GridInput {
	private final List<GridResponder> responders = new ArrayList<>();
	
	@Override
	public void addResponder(GridResponder responder) {
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
