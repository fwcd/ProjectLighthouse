package lighthouse.ui.sidebar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.style.Skin;
import com.alee.managers.style.StyleManager;
import com.alee.skin.dark.DarkSkin;
import com.alee.skin.web.WebSkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameMenuEntry;
import lighthouse.model.AppModel;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.debug.AnimationTracker;
import lighthouse.ui.debug.DebugToolsViewController;
import lighthouse.ui.scene.AnimationRunner;
import lighthouse.ui.scene.viewmodel.graphics.AnimatedImageAnimation;
import lighthouse.ui.scene.viewmodel.graphics.AnimatedResourceGIFAnimation;
import lighthouse.ui.scene.viewmodel.graphics.Animation;
import lighthouse.ui.scene.viewmodel.graphics.ConfettiAnimation;
import lighthouse.ui.scene.viewmodel.graphics.DemoAnimation;
import lighthouse.ui.scene.viewmodel.graphics.MovingImageAnimation;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.ui.util.ResourceImageLoader;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class MenuBarViewController implements SwingViewController {
	private static final Logger LOG = LoggerFactory.getLogger(MenuBarViewController.class);
	private final WebMenuBar component;
	private final PathChooser pathChooser;
	private final WebMenu gameMenu;

	private final AppModel model;
	private final AnimationRunner animationRunner;
	private AnimationTracker animationTracker = null;
	
	public MenuBarViewController(AppModel model, AnimationRunner animationRunner) {
		this.model = model;
		this.animationRunner = animationRunner;
		
		ResourceImageLoader resourceLoader = ResourceImageLoader.getInstance();
		
		gameMenu = LayoutUtils.menuOf("Game", resourceLoader.getAsIcon("/icons/game.png"));
		component = LayoutUtils.menuBarOf(
			LayoutUtils.menuOf("File", resourceLoader.getAsIcon("/icons/file.png"),
				LayoutUtils.itemOf("Save", this::save),
				LayoutUtils.itemOf("Save As", this::saveAs),
				LayoutUtils.itemOf("Open", this::open)
			),
			LayoutUtils.menuOf("UI", resourceLoader.getAsIcon("/icons/ui.png"),
				LayoutUtils.itemOf("Light theme", this::switchToLightTheme),
				LayoutUtils.itemOf("Dark theme", this::switchToDarkTheme)
			),
			LayoutUtils.menuOf("Debug", resourceLoader.getAsIcon("/icons/debug.png"),
				LayoutUtils.itemOf("Open debug tools", this::openDebugTools),
				LayoutUtils.itemOf("Play demo animation", () -> playAnimation(new DemoAnimation())),
				LayoutUtils.itemOf("Make it 'splode", () -> explode()),
				LayoutUtils.itemOf("Play confetti", () -> playAnimation(new ConfettiAnimation())),
				LayoutUtils.itemOf("Play sailing-boat", () -> {
					IntVec gameGridSize = getGameGridSize();
					playAnimation(new MovingImageAnimation(ResourceImageLoader.getInstance().get("/images/boat.png"), new IntVec(-gameGridSize.getX(), 0), new IntVec(gameGridSize.getX(), 0), gameGridSize.toDouble()));
				})
			),
			gameMenu
		);
		pathChooser = new PathChooser(component, ".json");
	}
	
	public void onOpen(Game game) {
		JPopupMenu gamePopupMenu = gameMenu.getPopupMenu();
		gamePopupMenu.removeAll();
		
		for (GameMenuEntry entry : game.getGameMenuEntries()) {
			gamePopupMenu.add(LayoutUtils.itemOf(entry.getLabel(), entry.getAction()));
		}
	}
	
	private void save() {
		Path destination = model.getSaveState().getSaveDestination();
		if (destination == null) {
			saveAs();
		} else {
			try {
				model.getActiveGameState().saveLevelTo(destination);
			} catch (Exception e) {
				showWarning(e);
			}
		}
	}
	
	private void saveAs() {
		pathChooser.showSaveDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getActiveGameState().saveLevelTo(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void open() {
		pathChooser.showOpenDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getActiveGameState().loadLevelFrom(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void playAnimation(Animation animation) {
		animationRunner.play(animation);
	}
	
	private IntVec getGameGridSize() {
		return model.getActiveGameState().getGridSize();
	}
	
	private void explode() {
		AnimatedImageAnimation animation = new AnimatedResourceGIFAnimation("/gifs/explosion.gif", DoubleVec.ZERO, getGameGridSize().toDouble());
		animation.setSpeed(0.5);
		playAnimation(animation);
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
	
	private void openDebugTools() {
		DebugToolsViewController debugTools = new DebugToolsViewController(model, animationTracker);
		WebFrame popup = new WebFrame("Debug tools");
		popup.setDefaultCloseOperation(WebFrame.DO_NOTHING_ON_CLOSE);
		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				debugTools.close();
				e.getWindow().dispose();
			}
		});
		popup.add(debugTools.getComponent());
		popup.setSize(640, 480);
		popup.setVisible(true);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
