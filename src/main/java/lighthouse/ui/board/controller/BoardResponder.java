package lighthouse.ui.board.controller;

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
	default void press(IntVec gridPos) {}
	
	default void rightPress(IntVec gridPos) {}
	
	default void dragTo(IntVec gridPos) {}
	
	default void release(IntVec gridPos) {}
}
