package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.ViewController;

public class AIControlsViewController implements ViewController {
	private final JComponent component;
	
	public AIControlsViewController() {
		component = new JPanel();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
