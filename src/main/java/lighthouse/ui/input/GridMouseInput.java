package lighthouse.ui.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.controller.GridResponder;

public class GridMouseInput extends MouseAdapter implements GridInput {
	private final List<GridResponder> responders = new ArrayList<>();
	
	@Override
	public void addResponder(GridResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO
	}
}
