package lighthouse.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EmptyViewController implements ViewController {
	private final JComponent component = new JPanel();
	
	@Override
	public JComponent getComponent() { return component; }
}
