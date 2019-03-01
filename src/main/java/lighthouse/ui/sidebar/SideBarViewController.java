package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.view.LocalLighthouseView;

/**
 * Manages the sidebar view which in turn deals
 * with the local and remote presentation of
 * the Lighthouse grid.
 */
public class SideBarViewController implements ViewController {
	private final JPanel component;
	
	public SideBarViewController(AppModel model, GameViewController game) {
		component = new JPanel(new BorderLayout());
		
		// Adds a menu bar on top
		MenuBarViewController menuBar = new MenuBarViewController(model, game);
		component.add(menuBar.getComponent(), BorderLayout.NORTH);
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		accordion.setFillSpace(false);
		
		// Adds a panel containing game and file controls
		GameControlsViewController gameControls = new GameControlsViewController(game, model);
		accordion.addPane("Game Controls", gameControls.getComponent());
		
		// Adds a panel containing AI controls
		AIViewController aiControls = new AIViewController(model);
		accordion.addPane("AI Controls", aiControls.getComponent());
		
		GameStatisticsViewController statistics = new GameStatisticsViewController(game.getContext().getStatistics());
		accordion.addPane("Game Statistics", statistics.getComponent()).collapse();
		
		// Add the connector panel which allows the user
		// to connect to the Lighthouse.
		LighthouseConnectorViewController connector = new LighthouseConnectorViewController(game.getBoard());
		accordion.addPane("Lighthouse Connector", connector.getComponent());
		
		// Add a small preview that accurately reflects the Lighthouse's grid.
		LocalLighthouseView preview = new LocalLighthouseView();
		game.getBoard().addLighthouseGridView(preview);
		preview.getComponent().setPreferredSize(new Dimension(180, 200));
		accordion.addPane("Lighthouse Preview", preview.getComponent());
		
		component.add(accordion, BorderLayout.CENTER);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
