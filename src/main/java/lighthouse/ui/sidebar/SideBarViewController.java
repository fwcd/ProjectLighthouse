package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.model.AppModel;
import lighthouse.ui.grid.GridViewController;

/**
 * Manages the sidebar view which in turn deals
 * with the local and remote presentation of
 * the Lighthouse grid.
 */
public class SideBarViewController {
	private final JPanel component;
	
	public SideBarViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		GridViewController grid = new GridViewController(model.getGrid());
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		accordion.setFillSpace(false);
		
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(grid);
		accordion.addPane("Lighthouse Connector", connector.getComponent());
		
		JComponent previewComponent = grid.getLocalComponent();
		previewComponent.setPreferredSize(new Dimension(120, 240));
		accordion.addPane("Lighthouse Preview", previewComponent);
		
		// accordion.setMaximumSize(accordion.getPreferredSize());
		component.add(accordion);
	}

	public JComponent getComponent() {
		return component;
	}
}
