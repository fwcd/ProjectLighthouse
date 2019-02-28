package lighthouse.ui.sidebar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.style.Skin;
import com.alee.managers.style.StyleManager;
import com.alee.skin.dark.DarkSkin;
import com.alee.skin.web.WebSkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.overlay.DemoAnimation;
import lighthouse.ui.debug.ListenerGraphViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.ui.util.ResourceIcon;

public class MenuBarViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(MenuBarViewController.class);
	private final WebMenuBar component;

	private final PathChooser pathChooser;
	private final AppModel model;
	private final GameViewController game;
	
	public MenuBarViewController(AppModel model, GameViewController game) {
		this.model = model;
		this.game = game;
		
		component = LayoutUtils.menuBarOf(
			LayoutUtils.menuOf("File", new ResourceIcon("/icons/file.png").get(),
				LayoutUtils.itemOf("Save", this::save),
				LayoutUtils.itemOf("Save As", this::saveAs),
				LayoutUtils.itemOf("Open", this::open)
			),
			LayoutUtils.menuOf("UI", new ResourceIcon("/icons/ui.png").get(),
				LayoutUtils.itemOf("Light theme", this::switchToLightTheme),
				LayoutUtils.itemOf("Dark theme", this::switchToDarkTheme)
			),
			LayoutUtils.menuOf("Debug", new ResourceIcon("/icons/debug.png").get(),
				LayoutUtils.itemOf("Show Listener graph", this::showListenerGraph),
				LayoutUtils.itemOf("Play Demo animation", this::playDemoAnimation)
			)
		);
		pathChooser = new PathChooser(component, ".json");
	}
	
	private void save() {
		Path destination = model.getSaveState().getSaveDestination();
		if (destination == null) {
			saveAs();
		} else {
			try {
				model.getGameState().saveLevelTo(destination);
			} catch (Exception e) {
				showWarning(e);
			}
		}
	}
	
	private void saveAs() {
		pathChooser.showSaveDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getGameState().saveLevelTo(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void open() {
		pathChooser.showOpenDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getGameState().loadLevelFrom(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void playDemoAnimation() {
		game.getBoard().play(new DemoAnimation());
	}
	
	private void switchToLightTheme() { applySkin(new WebSkin()); }
	
	private void switchToDarkTheme() { applySkin(new DarkSkin()); }
	
	private void applySkin(Skin skin) {
		SwingUtilities.invokeLater(() -> {
			StyleManager.setSkin(skin);
		});
	}
	
	private void showWarning(Exception e) {
		LOG.warn("Error while saving/loading files", e);
		JOptionPane.showMessageDialog(component, e.getMessage(), e.getClass().getSimpleName() + " while saving/loading a file", JOptionPane.WARNING_MESSAGE);
	}
	
	private void showListenerGraph() {
		ListenerGraphViewController graph = new ListenerGraphViewController(model, game);
		WebFrame popup = new WebFrame("Listener graph");
		popup.setDefaultCloseOperation(WebFrame.DO_NOTHING_ON_CLOSE);
		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				graph.removeUpdateHooks();
				e.getWindow().dispose();
			}
		});
		popup.add(graph.getComponent());
		popup.setSize(640, 480);
		popup.setVisible(true);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
