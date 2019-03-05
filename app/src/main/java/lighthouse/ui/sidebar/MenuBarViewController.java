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
import lighthouse.ui.board.viewmodel.overlay.AnimatedImageAnimation;
import lighthouse.ui.board.viewmodel.overlay.AnimatedResourceGIFAnimation;
import lighthouse.ui.board.viewmodel.overlay.Animation;
import lighthouse.ui.board.viewmodel.overlay.ConfettiAnimation;
import lighthouse.ui.board.viewmodel.overlay.DemoAnimation;
import lighthouse.ui.board.viewmodel.overlay.MovingImageAnimation;
import lighthouse.ui.debug.DebugToolsViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.ui.util.ResourceImageLoader;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class MenuBarViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(MenuBarViewController.class);
	private final WebMenuBar component;

	private final PathChooser pathChooser;
	private final AppModel model;
	private final GameViewController game;
	
	public MenuBarViewController(AppModel model, GameViewController game) {
		this.model = model;
		this.game = game;
		
		ResourceImageLoader resourceLoader = ResourceImageLoader.getInstance();
		IntVec boardSize = getBoardSize();
		
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
				LayoutUtils.itemOf("Play sailing-boat", () -> playAnimation(new MovingImageAnimation(ResourceImageLoader.getInstance().get("/images/boat.png"), new IntVec(-boardSize.getX(), 0), new IntVec(boardSize.getX(), 0), getBoardSize().toDouble())))));
		pathChooser = new PathChooser(component, ".json");
	}

	private IntVec getBoardSize() {
		return model.getGameState().getBoard().getSize();
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
	
	private void playAnimation(Animation animation) {
		game.getBoard().play(animation);
	}
	
	private void explode() {
		AnimatedImageAnimation animation = new AnimatedResourceGIFAnimation("/gifs/explosion.gif", DoubleVec.ZERO, getBoardSize().toDouble());
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
		DebugToolsViewController debugTools = new DebugToolsViewController(model, game);
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
