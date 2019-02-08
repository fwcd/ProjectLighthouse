package lighthouse.ui.controller;

/**
 * An interface for responding to user events.
 */
public interface GridResponder {
	void press(int gridX, int gridY);
	
	void dragTo(int gridX, int gridY);
	
	void release(int gridX, int gridY);
}
