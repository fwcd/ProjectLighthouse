package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import com.alee.laf.tabbedpane.WebTabbedPane;

import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.SceneViewController;

public class ConnectorsViewController implements SwingViewController {
	private final WebTabbedPane component;
	
	public ConnectorsViewController(SceneViewController scene) {
		component = new WebTabbedPane();
		
		component.addTab("Lighthouse", new LighthouseConnectorViewController(scene).getComponent());
		component.addTab("Discord", new DiscordConnectorViewController(scene).getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
