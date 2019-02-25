package lighthouse.ui.board.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.util.IntVec;

/**
 * A mouse-based grid input.
 */
public class BoardMouseInput extends MouseAdapter implements BoardInput {
	private static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	private final List<BoardResponder> responders = new ArrayList<>();
	private final CoordinateMapper mapper;
	private int activeButton = -1;
	
	public BoardMouseInput(CoordinateMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		IntVec pos = gridPosOf(e);
		activeButton = e.getButton();
		if (activeButton == RIGHT_BUTTON) {
			responders.forEach(r -> r.rightPress(pos));
		} else {
			responders.forEach(r -> r.press(pos));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (activeButton != RIGHT_BUTTON) {
			IntVec pos = gridPosOf(e);
			responders.forEach(r -> r.dragTo(pos));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (activeButton != RIGHT_BUTTON) {
			IntVec pos = gridPosOf(e);
			responders.forEach(r -> r.release(pos));
		}
		activeButton = -1;
	}
	
	private IntVec gridPosOf(MouseEvent e) {
		return mapper.toGridPos(new IntVec(e.getX(), e.getY()));
	}
}
