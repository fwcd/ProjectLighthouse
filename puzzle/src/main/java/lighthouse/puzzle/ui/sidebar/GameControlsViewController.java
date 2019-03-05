package lighthouse.puzzle.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.window.WebPopup;

import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.GameViewController;
import lighthouse.puzzle.ui.modes.EditingMode;
import lighthouse.puzzle.ui.modes.PlayingMode;
import lighthouse.ui.AppContext;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.ui.util.StatusBar;

/**
 * Manages a view containing game and file controls and is responsible for
 * presenting a path chooser to the user.
 */
public class GameControlsViewController implements ViewController {
	private final JComponent component;
	private final StatusBar statusBar;

	public GameControlsViewController(GameViewController game, PuzzleGameState gameState) {
		component = new JPanel();
		component.setLayout(new BorderLayout());

		AppContext context = game.getContext();
		
		// Setup status bar
		statusBar = new StatusBar();
		statusBar.display(context.getStatus());
		
		context.getStatusListeners().add(statusBar::display);
		gameState.getBoardListeners().add(newBoard -> {
			context.getStatusListeners().add(statusBar::display);
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
