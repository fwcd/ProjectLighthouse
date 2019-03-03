package lighthouse.ui.board.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.util.IntVec;

/**
 * A keyboard grid input implementation.
 */
public class BoardKeyInput extends KeyAdapter implements BoardInput {
	private final List<BoardResponder> responders = new ArrayList<>();
	private IntVec selectionPos = null;
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	public void keyPressed(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_UP:
				arrowUpPressed();
				break;
			case KeyEvent.VK_DOWN:
				arrowDownPressed();
				break;
			case KeyEvent.VK_LEFT:
				arrowLeftPressed();
				break;
			case KeyEvent.VK_RIGHT:
				arrowRightPressed();
				break;
			default:
				break;
		}
	}
	
	public void arrowUpPressed() {
		arrowKeyPressed(r -> r.selectUp(selectionPos));
	}
	
	public void arrowDownPressed() {
		arrowKeyPressed(r -> r.selectDown(selectionPos));
	}
	
	public void arrowLeftPressed() {
		arrowKeyPressed(r -> r.selectLeft(selectionPos));
	}
	
	public void arrowRightPressed() {
		arrowKeyPressed(r -> r.selectRight(selectionPos));
	}
	
	private void arrowKeyPressed(Function<BoardResponder, IntVec> directionedSelectAction) {
		if (hasSelection()) {
			updateSelection(directionedSelectAction);
		} else {
			updateSelection(r -> r.selectAny());
		}
	}
	
	private void updateSelection(Function<BoardResponder, IntVec> action) {
		boolean updatedSelection = false;
		for (BoardResponder responder : responders) {
			IntVec result = action.apply(responder);
			
			if (!updatedSelection) {
				selectionPos = result;
				updatedSelection = true;
			}
		}
	}
	
	private boolean hasSelection() {
		return selectionPos != null;
	}
	
	public void keyReleased(int keyCode) {
		responders.forEach(r -> r.deselect());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keyReleased(e.getKeyCode());
	}
}
