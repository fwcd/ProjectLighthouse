package lighthouse.ui.util;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class SwapPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public SwapPanel() {
		setLayout(new BorderLayout());
	}
	
	public void swapTo(Component newComponent) {
		removeAll();
		add(newComponent, BorderLayout.CENTER);
	}
}
