package lighthouse.ui.sidebar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lighthouse.ui.board.BoardViewController;

public class GameControlsViewController {
	private final JComponent component;
	private final JLabel statusLabel;
	
	public GameControlsViewController(BoardViewController board) {
		component = new JPanel();
		
		statusLabel = new JLabel();
		component.add(statusLabel);
		
		component.add(buttonOf("New Game", board::newGame));
		component.add(buttonOf("Edit", board::edit));
	}
	
	public void setStatus(String status) {
		statusLabel.setText(status);
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
