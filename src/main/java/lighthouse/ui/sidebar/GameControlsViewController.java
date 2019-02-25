package lighthouse.ui.sidebar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GameControlsViewController {
	private final JComponent component;
	
	public GameControlsViewController() {
		component = new JPanel();
		
		component.add(buttonOf("New Game", () -> {}));
		component.add(buttonOf("Edit", () -> {}));
	}
	
	private JButton buttonOf(String label, Runnable action) {
		JButton button = new JButton(label);
		button.addActionListener(l -> action.run());
		return button;
	}
	
	public JComponent getComponent() {
		return component;
	}
}
