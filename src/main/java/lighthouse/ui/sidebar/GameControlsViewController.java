package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.nio.file.Path;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.model.BoardEditState;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.loop.GameLoop;

/**
 * Manages a view containing game and file
 * controls and is responsible for presenting
 * a path chooser to the user.
 */
public class GameControlsViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(GameControlsViewController.class);
	private final JComponent component;
	private final StatusBar statusBar;
	
	private final PathChooser pathChooser;
	private final AppModel model;

	public GameControlsViewController(BoardViewController board, AppModel model, GameLoop loop) {
		this.model = model;
		
		component = new JPanel();
		pathChooser = new PathChooser(component, ".json");
		component.setLayout(new BorderLayout());
		
		// Setup status bar
		BoardEditState editState = board.getModel().getEditState();
		statusBar = new StatusBar();
		statusBar.display(editState.getStatus());
		component.add(statusBar.getComponent(), BorderLayout.NORTH);
		
		editState.getStatusListeners().add(statusBar::display);
		model.getGame().getBoardListeners().add(newBoard -> {
			newBoard.getEditState().getStatusListeners().add(statusBar::display);
		});
		
		// Setup control panel
		component.add(vboxOf(
			panelOf(
				buttonOf("New Game", board::newGame),
				buttonOf("Reset", board::reset),
				buttonOf("Edit", board::edit)
			),
			panelOf(
				buttonOf("Save", this::save),
				buttonOf("Save As", this::saveAs),
				buttonOf("Open", this::open)
			),
			new LevelNavigatorViewController(model.getGame(), loop).getComponent()
		), BorderLayout.CENTER);
	}
	
	private void save() {
		Path destination = model.getSaveState().getSaveDestination();
		if (destination == null) {
			saveAs();
		} else {
			try {
				model.getGame().saveLevelTo(destination);
			} catch (Exception e) {
				showWarning(e);
			}
		}
	}
	
	private void saveAs() {
		pathChooser.showSaveDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getGame().saveLevelTo(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void open() {
		pathChooser.showOpenDialog().ifPresent(path -> {
			model.getSaveState().setSaveDestination(path);
			try {
				model.getGame().loadLevelFrom(path);
			} catch (Exception e) {
				showWarning(e);
			}
		});
	}
	
	private void showWarning(Exception e) {
		LOG.warn("Error while saving/loading files", e);
		JOptionPane.showMessageDialog(component, e.getMessage(), e.getClass().getSimpleName() + " while saving/loading a file", JOptionPane.WARNING_MESSAGE);
	}
	
	private JPanel vboxOf(JComponent... components) {
		JPanel vbox = new JPanel();
		vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
		for (JComponent child : components) {
			vbox.add(child);
		}
		return vbox;
	}
	
	private JPanel panelOf(JComponent... components) {
		JPanel bar = new JPanel();
		for (JComponent child : components) {
			bar.add(child);
		}
		return bar;
	}
	
	private JButton buttonOf(String label, Runnable action) {
		JButton button = new JButton(label);
		button.addActionListener(l -> action.run());
		return button;
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
