package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.model.AppModel;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.view.LocalLighthouseGridView;

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
		
		BoardViewController grid = new BoardViewController(model.getBoard());
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		accordion.setFillSpace(false);
		
		// Add the connector panel which allows the user
		// to connect to the Lighthouse.
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(grid);
		accordion.addPane("Lighthouse Connector", connector.getComponent());
		
		// Add a small preview that accurately reflects the Lighthouse's grid.
		LocalLighthouseGridView preview = new LocalLighthouseGridView();
		grid.addLighthouseGridView(preview);
		preview.getComponent().setPreferredSize(new Dimension(120, 240));
		accordion.addPane("Lighthouse Preview", preview.getComponent());
		
		component.add(accordion);
	}

	public JComponent getComponent() {
		return component;
	}
}
