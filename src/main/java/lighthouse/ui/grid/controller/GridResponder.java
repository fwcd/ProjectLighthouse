package lighthouse.ui.grid.controller;

/**
 * An interface for responding to user events.
 * 
 * <p>Inputs are already processed to a more domain-
 * specific version since raw input events may look
 * differently across inputs (a mouse might use
 * exact mouse coordinates while an Xbox controller
 * would only receive offsets).</p>
 */
public interface GridResponder {
	void press(int gridX, int gridY);
	
	void dragTo(int gridX, int gridY);
	
	void release(int gridX, int gridY);
}
