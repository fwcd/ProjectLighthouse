package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.AppModel;
import lighthouse.ui.loop.GameLoop;
import lighthouse.ui.sidebar.SideBarViewController;
import lighthouse.ui.util.CenterPanel;

/**
 * The application's base view controller.
 */
public class AppViewController implements ViewController {
	private final JComponent component;
	private final GameLoop loop = new GameLoop();
	
	public AppViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		GameViewController game = new GameViewController(model.getGame());
		component.add(new CenterPanel(game.getComponent()), BorderLayout.CENTER);
		
		SideBarViewController sideBar = new SideBarViewController(model, game, loop);
		component.add(sideBar.getComponent(), BorderLayout.EAST);
		
		// Start the game loop
		loop.addRenderer(game.getBoard());
		loop.start();
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
