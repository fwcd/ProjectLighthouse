package lighthouse.ui.board.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.board.controller.BoardResponder;

/**
 * A mouse-based grid input.
 */
public class BoardMouseInput extends MouseAdapter implements BoardInput {
	private final List<BoardResponder> responders = new ArrayList<>();
	private final int cellWidth;
	private final int cellHeight;
	
	public BoardMouseInput(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int gridX = toBoardX(e.getX());
		int gridY = toBoardY(e.getY());
		responders.forEach(r -> r.press(gridX, gridY));
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int gridX = toBoardX(e.getX());
		int gridY = toBoardY(e.getY());
		responders.forEach(r -> r.dragTo(gridX, gridY));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int gridX = toBoardX(e.getX());
		int gridY = toBoardY(e.getY());
		responders.forEach(r -> r.release(gridX, gridY));
	}
	
	/** Converts a pixel x-coordinate to a grid coordinate. */
	private int toBoardX(int pixelX) {
		return pixelX / cellWidth;
	}
	
	/** Converts a pixel y-coordinate to a grid coordinate. */
	private int toBoardY(int pixelY) {
		return pixelY / cellHeight;
	}
}
