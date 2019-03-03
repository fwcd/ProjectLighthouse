package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.window.WebPopup;

import lighthouse.model.AppModel;
import lighthouse.model.GameState;
import lighthouse.ui.GameContext;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.modes.EditingMode;
import lighthouse.ui.modes.PlayingMode;
import lighthouse.ui.util.LayoutUtils;

/**
 * Manages a view containing game and file controls and is responsible for
 * presenting a path chooser to the user.
 */
public class GameControlsViewController implements ViewController {
	private final JComponent component;
	private final StatusBar statusBar;

	public GameControlsViewController(GameViewController game, AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());

		GameContext context = game.getContext();
		GameState gameState = model.getGameState();
		
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
