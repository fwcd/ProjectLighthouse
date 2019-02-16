package lighthouse.ui.sidebar;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.ui.grid.GridViewController;

/**
 * Manages the sidebar view.
 */
public class SideBarViewController {
	private final JPanel component;
	
	public SideBarViewController(GridViewController grid) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(grid);
		accordion.addPane("Lighthouse Connector", connector.getComponent());
		
		accordion.setMaximumSize(accordion.getPreferredSize());
		component.add(accordion);
	}

	public JComponent getComponent() {
		return component;
	}
}
