package lighthouse.ui;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.alee.laf.tabbedpane.WebTabbedPane;

import lighthouse.gameapi.Game;
import lighthouse.model.AppModel;
import lighthouse.puzzle.PuzzleGame;
import lighthouse.puzzle.ui.GameViewController;
import lighthouse.ui.discordrpc.DiscordRPCRunner;
import lighthouse.ui.sidebar.SideBarViewController;

/**
 * The application's base view controller.
 */
public class AppViewController implements ViewController {
	private final JComponent component;
	private final JTabbedPane tabPane;
	private final SideBarViewController sideBar;
	
	private final AppContext context = new AppContext();
	private final DiscordRPCRunner discordRPC = new DiscordRPCRunner();
	
	private final Set<Game> gameRegistry = new HashSet<>();
	
	public AppViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		tabPane = new WebTabbedPane();
		component.add(tabPane, BorderLayout.CENTER);
		
		GameViewController game = new GameViewController(model.getGameState());
		sideBar = new SideBarViewController();
		component.add(sideBar.getComponent(), BorderLayout.EAST);
		
		// Register known games
		registerGames();

		// Setup RPC
		discordRPC.setState(context.getStatus().getMessage());
		discordRPC.updatePresenceSoon();
		discordRPC.start();
		
		context.getStatusListeners().add(newStatus -> {
			discordRPC.setState(newStatus.getMessage());
			discordRPC.updatePresenceSoon();
		});
	}
	
	private void registerGames() {
		registerGame(new PuzzleGame());
	}
	
	public void registerGame(Game game) {
		game.initialize(context);
		
		tabPane.addTab(game.getName(), game.getGameViewController().getComponent());
		tabPane.addChangeListener(l -> open(game));
		
		if (gameRegistry.isEmpty()) {
			open(game);
		}
		
		gameRegistry.add(game);
	}
	
	private void open(Game game) {
		sideBar.setGameControls(game.getControlsViewController().getComponent());
		sideBar.setGameStatistics(game.getStatisticsViewController().getComponent());
		sideBar.setSolver(game.getSolverViewController().getComponent());
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
