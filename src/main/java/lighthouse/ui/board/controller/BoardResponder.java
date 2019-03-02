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
	
	default void select(IntVec gridPos) {}
	
	default void deselect(IntVec gridPos) {}
	
	default void reset() {}
}
