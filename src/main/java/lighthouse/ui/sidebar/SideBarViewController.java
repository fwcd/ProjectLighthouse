package lighthouse.ui.sidebar;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.grid.GridViewController;

/**
 * Manages the sidebar view.
 */
public class SideBarViewController {
	private final JComponent component;
	
	public SideBarViewController(GridViewController grid) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(grid);
		component.add(connector.getComponent());
	}

	public JComponent getComponent() {
		return component;
	}
}
