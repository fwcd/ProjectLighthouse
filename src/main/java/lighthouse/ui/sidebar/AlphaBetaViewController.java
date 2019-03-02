package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.ViewController;

public class AlphaBetaViewController implements ViewController {
	private final JPanel component;
	
	public AlphaBetaViewController() {
		component = new JPanel();
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
