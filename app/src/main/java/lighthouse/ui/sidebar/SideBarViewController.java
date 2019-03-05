package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.panel.WebAccordion;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.scene.view.LocalLighthouseView;
import lighthouse.ui.util.SwapPanel;

/**
 * Manages the sidebar view which in turn deals
 * with the local and remote presentation of
 * the Lighthouse grid.
 */
public class SideBarViewController implements ViewController {
	private final JPanel component;
	private final SwapPanel gameControlPanel;
	private final SwapPanel gameStatisticsPanel;
	private final SwapPanel solverPanel;
	
	public SideBarViewController(AppModel model, GameViewController game) {
		component = new JPanel(new BorderLayout());
		
		// Adds a menu bar on top
		MenuBarViewController menuBar = new MenuBarViewController(model, game);
		component.add(menuBar.getComponent(), BorderLayout.NORTH);
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMultiplySelectionAllowed(true);
		accordion.setFillSpace(false);
		
		// Adds a panel containing game-specific controls
		gameControlPanel = new SwapPanel();
		accordion.addPane("Game Controls", gameControlPanel);
		
		// TODO: Integrate AI controls into solvers
		// AIViewController aiControls = new AIViewController(model);
		// accordion.addPane("AI Controls", aiControls.getComponent()).collapse();
		
		solverPanel = new SwapPanel();
		accordion.addPane("Solver", solverPanel);
		
		// Adds a panel containing game-specific statistics
		gameStatisticsPanel = new SwapPanel();
		accordion.addPane("Game Statistics", gameStatisticsPanel).collapse();
		
		// Add the connector panel which allows the user
		// to connect a remote Lighthouse view.
		ConnectorsViewController connector = new ConnectorsViewController(game.getBoard());
		accordion.addPane("Connectors", connector.getComponent());
		
		// Add a small preview that accurately reflects the Lighthouse's grid.
		LocalLighthouseView preview = new LocalLighthouseView();
		game.getBoard().addLighthouseView(preview);
		preview.getComponent().setPreferredSize(new Dimension(180, 200));
		accordion.addPane("Lighthouse Preview", preview.getComponent());
		
		component.add(accordion, BorderLayout.CENTER);
	}
	
	public void setGameControls(JComponent newComponent) {
		gameControlPanel.swapTo(newComponent);
	}
	
	public void setGameStatistics(JComponent newComponent) {
		gameStatisticsPanel.swapTo(newComponent);
	}
	
	public void setSolver(JComponent newComponent) {
		solverPanel.swapTo(newComponent);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
