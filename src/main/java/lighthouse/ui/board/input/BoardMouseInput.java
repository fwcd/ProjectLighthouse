package lighthouse.ui.board.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.util.IntVec;
import lighthouse.util.transform.Bijection;

/**
 * A mouse-based grid input.
 */
public class BoardMouseInput extends MouseAdapter implements BoardInput {
	private final List<BoardResponder> responders = new ArrayList<>();
	private final Bijection<IntVec> gridToPixels;
	
	public BoardMouseInput(Bijection<IntVec> gridToPixels) {
		this.gridToPixels = gridToPixels;
	}
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		IntVec pixelPos = pixelPosOf(e);
		IntVec gridPos = gridToPixels.inverse(pixelPos);
		if (SwingUtilities.isRightMouseButton(e)) {
			responders.forEach(r -> r.rightPress(gridPos));
		} else {
			responders.forEach(r -> r.press(gridPos));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SwingUtilities.isRightMouseButton(e)) {
			IntVec pixelPos = pixelPosOf(e);
			IntVec gridPos = gridToPixels.inverse(pixelPos);
			responders.forEach(r -> r.dragTo(gridPos));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isRightMouseButton(e)) {
			IntVec pixelPos = pixelPosOf(e);
			IntVec gridPos = gridToPixels.inverse(pixelPos);
			responders.forEach(r -> r.release(gridPos));
		}
	}
	
	private IntVec pixelPosOf(MouseEvent e) {
		return new IntVec(e.getX(), e.getY());
	}
}
