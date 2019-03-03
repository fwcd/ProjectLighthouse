package lighthouse.ui.board.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.util.IntVec;

/**
 * A keyboard grid input implementation.
 */
public class BoardKeyInput extends KeyAdapter implements BoardInput {
	private final Map<Integer, Runnable> bindings = new HashMap<>();
	private final List<BoardResponder> responders = new ArrayList<>();
	private IntVec selectionPos = null;
	
	public BoardKeyInput() {
		bindings.put(KeyEvent.VK_UP, this::arrowUpPressed);
		bindings.put(KeyEvent.VK_DOWN, this::arrowDownPressed);
		bindings.put(KeyEvent.VK_LEFT, this::arrowLeftPressed);
		bindings.put(KeyEvent.VK_RIGHT, this::arrowRightPressed);
	}
	
	@Override
	public void addResponder(BoardResponder responder) {
		responders.add(responder);
	}
	
	public void keyPressed(int keyCode) {
		Runnable action = bindings.get(keyCode);
		if (action != null) {
			action.run();
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
	
	public Map<Integer, Runnable> getBindings() {
		return bindings;
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
