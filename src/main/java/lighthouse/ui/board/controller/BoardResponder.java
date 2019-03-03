package lighthouse.ui.board.controller;

import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;

/**
 * An interface for responding to user events.
 * 
 * <p>Inputs are already processed to a more domain-
 * specific version since raw input events may look
 * differently across inputs (a mouse might use
 * exact mouse coordinates while an Xbox controller
 * would only receive offsets).</p>
 */
public interface BoardResponder {
	void updateViewModel(BoardViewModel viewModel);
	
	default void press(IntVec gridPos) {}
	
	default void rightPress(IntVec gridPos) {}
	
	default void dragTo(IntVec gridPos) {}
	
	default void release(IntVec gridPos) {}
		
	default IntVec selectAny() { return null; }

	default IntVec select(IntVec gridPos) { return null; }
	
	default IntVec selectUp(IntVec gridPos) { return null; }
	
	default IntVec selectLeft(IntVec gridPos) { return null; }
	
	default IntVec selectDown(IntVec gridPos) { return null; }
	
	default IntVec selectRight(IntVec gridPos) { return null; }
	
	default void deselect() {}
	
	default void reset() {}
}
