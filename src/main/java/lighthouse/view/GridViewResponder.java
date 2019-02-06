package lighthouse.view;

/**
 * An interface for responding to user events.
 */
public interface GridViewResponder {
	void mouseDown(int gridX, int gridY);
	
	void mouseDrag(int gridX, int gridY);
	
	void mouseUp(int gridX, int gridY);
}
