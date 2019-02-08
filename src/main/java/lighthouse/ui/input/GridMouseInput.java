package lighthouse.ui.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.controller.GridResponder;

/**
 * A mouse-based grid input.
 */
public class GridMouseInput extends MouseAdapter implements GridInput {
	private final List<GridResponder> responders = new ArrayList<>();
	private final int cellWidth;
	private final int cellHeight;
	
	public GridMouseInput(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}
	
	@Override
	public void addResponder(GridResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int gridX = toGridX(e.getX());
		int gridY = toGridY(e.getY());
		responders.forEach(r -> r.press(gridX, gridY));
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int gridX = toGridX(e.getX());
		int gridY = toGridY(e.getY());
		responders.forEach(r -> r.dragTo(gridX, gridY));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int gridX = toGridX(e.getX());
		int gridY = toGridY(e.getY());
		responders.forEach(r -> r.release(gridX, gridY));
	}
	
	/** Converts a pixel x-coordinate to a grid coordinate. */
	private int toGridX(int pixelX) {
		return pixelX / cellWidth;
	}
	
	/** Converts a pixel y-coordinate to a grid coordinate. */
	private int toGridY(int pixelY) {
		return pixelY / cellHeight;
	}
}
