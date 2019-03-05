package lighthouse.ui.util;

import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A simple wrapper that centers another
 * Swing component.
 */
public class CenterPanel extends JPanel {
	private static final long serialVersionUID = 6492468233455759878L;
	
	public CenterPanel(JComponent wrapped) {
		setLayout(new GridBagLayout());
		add(wrapped);
	}
}
