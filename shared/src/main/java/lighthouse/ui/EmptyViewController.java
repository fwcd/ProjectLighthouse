package lighthouse.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EmptyViewController implements SwingViewController {
	private final JComponent component = new JPanel();
	
	@Override
	public JComponent getComponent() { return component; }
}
