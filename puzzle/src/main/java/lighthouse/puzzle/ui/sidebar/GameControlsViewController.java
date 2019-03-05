package lighthouse.puzzle.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.window.WebPopup;

import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.PuzzleGameManager;
import lighthouse.puzzle.ui.modes.EditingMode;
import lighthouse.puzzle.ui.modes.PlayingMode;
import lighthouse.ui.ObservableStatus;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.ui.util.StatusBar;

/**
 * Manages a view containing game and file controls and is responsible for
 * presenting a path chooser to the user.
 */
public class GameControlsViewController implements SwingViewController {
	private final JComponent component;
	private final StatusBar statusBar;

	public GameControlsViewController(PuzzleGameManager game, PuzzleGameState gameState) {
		component = new JPanel();
		component.setLayout(new BorderLayout());

		ObservableStatus status = game.getStatus();
		
		// Setup status bar
		statusBar = new StatusBar();
		statusBar.display(status.get());
		
		status.getListeners().add(statusBar::display);
		gameState.getBoardListeners().add(newBoard -> {
			status.getListeners().add(statusBar::display);
		});
		
		WebSplitButton editButton = LayoutUtils.splitButtonOf("Edit", () -> game.enter(EditingMode.INSTANCE));
		editButton.setPopupMenu(LayoutUtils.popupMenuOf("",
			LayoutUtils.itemOf("Edit blocked states", () -> {
				WebPopup popup = new WebPopup();
				popup.setFollowInvoker(true);
				popup.setSize(200, 200);
				popup.setLayout(new BorderLayout());
				popup.add(new BlockedStatesEditorViewController(gameState.getLevel()).getComponent(), BorderLayout.CENTER);
				popup.showPopup(editButton, 10, 10);
			})
		));
		
		// Setup control panel
		component.add(LayoutUtils.vboxOf(
			statusBar.getComponent(),
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Play", () -> game.enter(PlayingMode.INSTANCE)),
				LayoutUtils.buttonOf("Reset", game::reset),
				editButton
			),
			new LevelNavigatorViewController(game).getComponent()
		), BorderLayout.CENTER);
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
