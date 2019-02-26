package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lighthouse.model.BoardEditState;
import lighthouse.ui.board.BoardViewController;

public class GameControlsViewController {
	private final JComponent component;
	private final JLabel statusLabel;

	public GameControlsViewController(BoardViewController board) {
		component = new JPanel();
		component.setLayout(new BorderLayout());

		BoardEditState editState = board.getModel().getEditState();
		statusLabel = new JLabel(editState.getStatus());
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		component.add(statusLabel, BorderLayout.NORTH);
		editState.getStatusListeners().add(statusLabel::setText);
		
		JPanel buttons = new JPanel();
		buttons.add(buttonOf("New Game", board::newGame));
		buttons.add(buttonOf("Reset", board::reset));
		buttons.add(buttonOf("Edit", board::edit));
		component.add(buttons, BorderLayout.CENTER);
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
