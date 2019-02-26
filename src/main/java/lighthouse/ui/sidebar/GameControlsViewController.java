package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lighthouse.model.BoardEditState;
import lighthouse.model.FileSaveState;
import lighthouse.ui.board.BoardViewController;

/**
 * Manages a view containing game and file
 * controls and is responsible for presenting
 * a path chooser to the user.
 */
public class GameControlsViewController {
	private final JComponent component;
	private final JLabel statusLabel;
	
	private final BoardViewController board;
	private final PathChooser pathChooser;
	private final FileSaveState saveState;

	public GameControlsViewController(BoardViewController board, FileSaveState saveState) {
		this.saveState = saveState;
		this.board = board;
		
		component = new JPanel();
		pathChooser = new PathChooser(component);
		component.setLayout(new BorderLayout());

		BoardEditState editState = board.getModel().getEditState();
		statusLabel = new JLabel(editState.getStatus());
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		component.add(statusLabel, BorderLayout.NORTH);
		editState.getStatusListeners().add(statusLabel::setText);
		
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
			)
		), BorderLayout.CENTER);
	}
	
	private void save() {
		// TODO: Remember saved file
		saveAs();
	}
	
	private void saveAs() {
		pathChooser.showSaveDialog().ifPresent(path -> {
			saveState.setSaveDestination(path);
			board.save(path);
		});
	}
	
	private void open() {
		pathChooser.showOpenDialog().ifPresent(path -> {
			saveState.setSaveDestination(path);
			board.open(path);
		});
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
	
	public JComponent getComponent() {
		return component;
	}
}
