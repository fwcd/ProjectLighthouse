package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import com.alee.laf.tabbedpane.WebTabbedPane;

import lighthouse.ui.ViewController;
import lighthouse.puzzle.ui.board.BoardViewController;

public class ConnectorsViewController implements ViewController {
	private final WebTabbedPane component;
	
	public ConnectorsViewController(BoardViewController board) {
		component = new WebTabbedPane();
		
		component.addTab("Lighthouse", new LighthouseConnectorViewController(board).getComponent());
		component.addTab("Discord", new DiscordConnectorViewController(board).getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
