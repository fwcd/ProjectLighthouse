package lighthouse.ui.sidebar;

import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.alee.laf.menu.WebMenuBar;
import com.alee.managers.style.Skin;
import com.alee.managers.style.StyleManager;
import com.alee.skin.dark.DarkSkin;
import com.alee.skin.web.WebSkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class MenuBarViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(MenuBarViewController.class);
	private final WebMenuBar component;

	private final PathChooser pathChooser;
	private final AppModel model;
	
	public MenuBarViewController(AppModel model) {
		this.model = model;
		component = LayoutUtils.menuBarOf(
			LayoutUtils.menuOf("File",
				LayoutUtils.itemOf("Save", this::save),
				LayoutUtils.itemOf("Save As", this::saveAs),
				LayoutUtils.itemOf("Open", this::open)
			),
			LayoutUtils.menuOf("UI",
				LayoutUtils.itemOf("Light theme", this::switchToLightTheme),
				LayoutUtils.itemOf("Dark theme", this::switchToDarkTheme)
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
	
	@Override
	public JComponent getComponent() { return component; }
}
