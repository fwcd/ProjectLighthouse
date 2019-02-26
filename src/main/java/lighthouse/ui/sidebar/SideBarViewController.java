package lighthouse.ui.sidebar;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.view.LocalLighthouseGridView;
import lighthouse.ui.loop.GameLoop;

/**
 * Manages the sidebar view which in turn deals
 * with the local and remote presentation of
 * the Lighthouse grid.
 */
public class SideBarViewController implements ViewController {
	private final JPanel component;
	
	public SideBarViewController(AppModel model, GameViewController game, GameLoop loop) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		accordion.setFillSpace(false);
		
		// Adds a panel containing game and file controls
		GameControlsViewController controls = new GameControlsViewController(game, model, loop);
		accordion.addPane("Game Controls", controls.getComponent());
		
		// Add the connector panel which allows the user
		// to connect to the Lighthouse.
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(game.getBoard());
		accordion.addPane("Lighthouse Connector", connector.getComponent());
		
		// Add a small preview that accurately reflects the Lighthouse's grid.
		LocalLighthouseGridView preview = new LocalLighthouseGridView();
		game.getBoard().addLighthouseGridView(preview);
		preview.getComponent().setPreferredSize(new Dimension(120, 240));
		accordion.addPane("Lighthouse Preview", preview.getComponent());
		
		component.add(accordion);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
