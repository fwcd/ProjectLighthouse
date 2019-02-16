package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.grid.GridViewController;
import lighthouse.ui.grid.view.RemoteGridView;
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

/**
 * Manages a view containing the Lighthouse
 * connection controls.
 */
public class LighthouseConnectorViewController {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseConnectorViewController.class);
	private final JComponent component;
	
	public LighthouseConnectorViewController(GridViewController grid) {
		component = new JPanel();
		
		// Creates a remote Lighthouse view if the required login information is present
		ConfigFile auth = new ResourceConfigFile("/authentication.txt");
		if (auth.has("username") && auth.has("token")) {
			RemoteGridView remoteView = new RemoteGridView(auth.get("username"), auth.get("token"));
			remoteView.connect(); // TODO: Connect dynamically from the UI instead
			grid.addView(remoteView);
		} else {
			LOG.warn("Warning: Authentication did not contain 'username' and/or 'token'");
		}
	}
	
	public JComponent getComponent() {
		return component;
	}
}
