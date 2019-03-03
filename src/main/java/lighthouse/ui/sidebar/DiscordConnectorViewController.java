package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.ViewController;
import lighthouse.ui.board.BoardViewController;

public class DiscordConnectorViewController implements ViewController {
	private final JPanel component;
	
	public DiscordConnectorViewController(BoardViewController board) {
		component = new JPanel();
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
