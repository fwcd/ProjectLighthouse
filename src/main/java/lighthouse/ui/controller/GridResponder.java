package lighthouse.ui.controller;

/**
 * An interface for responding to user events.
 */
public interface GridResponder {
	void mouseDown(int gridX, int gridY);
	
	void mouseDrag(int gridX, int gridY);
	
	void mouseUp(int gridX, int gridY);
}
