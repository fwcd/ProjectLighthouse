package lighthouse.ui.debug.animations;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.ViewController;

public class ActiveAnimationsViewController implements ViewController {
	private final JPanel component;
	
	public ActiveAnimationsViewController() {
		component = new JPanel();
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
