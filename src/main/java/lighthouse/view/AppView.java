package lighthouse.view;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The application interface. Passively renders.
 */
public class AppView {
	private final JComponent component;
	
	public AppView() {
		component = new JPanel();
		
	}
	
	public JComponent getComponent() {
		return component;
	}
}
