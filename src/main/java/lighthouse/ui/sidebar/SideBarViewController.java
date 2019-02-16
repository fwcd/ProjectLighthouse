package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import lighthouse.ui.grid.GridViewController;

/**
 * Manages the sidebar view.
 */
public class SideBarViewController {
	private final JXTaskPaneContainer component;
	
	public SideBarViewController(GridViewController grid) {
		component = new JXTaskPaneContainer();
		
		JXTaskPane connectorPane = new JXTaskPane();
		connectorPane.setTitle("Lighthouse Connector");
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(grid);
		connectorPane.add(connector.getComponent());
		component.add(connectorPane);
	}

	public JComponent getComponent() {
		return component;
	}
}
