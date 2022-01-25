package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.accordion.WebAccordion;
import com.alee.utils.swing.extensions.SizeMethods;

import lighthouse.gameapi.Game;
import lighthouse.model.AppModel;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.SceneViewController;
import lighthouse.ui.scene.view.LocalLighthouseView;
import lighthouse.ui.util.SwapPanel;

/**
 * Manages the sidebar view which in turn deals
 * with the local and remote presentation of
 * the Lighthouse grid.
 */
public class SideBarViewController implements SwingViewController {
	private final JPanel component;
	
	private final MenuBarViewController menuBar;
	private final SwapPanel gameControlPanel;
	private final SwapPanel gameStatisticsPanel;
	private final SwapPanel solverPanel;
	
	public SideBarViewController(AppModel model, SceneViewController scene) {
		component = new JPanel(new BorderLayout());
		
		// Adds a menu bar on top
		menuBar = new MenuBarViewController(model, scene);
		component.add(menuBar.getComponent(), BorderLayout.NORTH);
		
		WebAccordion accordion = new WebAccordion();
		accordion.setMinimumExpandedPaneCount(0);
		accordion.setMaximumExpandedPaneCount(Integer.MAX_VALUE);
		accordion.setMinimumHeight(SizeMethods.UNDEFINED);
		
		// Adds a panel containing game-specific controls
		gameControlPanel = new SwapPanel();
		accordion.addPane("Game Controls", gameControlPanel).expand();
		
		solverPanel = new SwapPanel();
		accordion.addPane("Solver", solverPanel).expand();
		
		// Adds a panel containing game-specific statistics
		gameStatisticsPanel = new SwapPanel();
		accordion.addPane("Game Statistics", gameStatisticsPanel).collapse();
		
		// Add the connector panel which allows the user
		// to connect a remote Lighthouse view.
		ConnectorsViewController connector = new ConnectorsViewController(scene);
		accordion.addPane("Connectors", connector.getComponent()).expand();
		
		// Add a small preview that accurately reflects the Lighthouse's grid.
		LocalLighthouseView preview = new LocalLighthouseView();
		scene.addLighthouseView(preview);
		preview.getComponent().setPreferredSize(new Dimension(180, 200));
		accordion.addPane("Lighthouse Preview", preview.getComponent()).expand();
		
		component.add(accordion, BorderLayout.CENTER);
	}
	
	public void onOpen(Game game) {
		menuBar.onOpen(game);
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
