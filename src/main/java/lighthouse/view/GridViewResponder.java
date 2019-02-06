package lighthouse.view;

import java.awt.event.MouseEvent;

/**
 * An interface for responding to user events.
 */
public interface GridViewResponder {
	void mouseDown(MouseEvent e);
	
	void mouseDrag(MouseEvent e);
	
	void mouseUp(MouseEvent e);
}
