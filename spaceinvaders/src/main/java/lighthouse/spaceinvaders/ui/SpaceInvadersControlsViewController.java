package lighthouse.spaceinvaders.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.util.LayoutUtils;

public class SpaceInvadersControlsViewController implements SwingViewController {
    private final JPanel component = new JPanel();
    
    public SpaceInvadersControlsViewController(SpaceInvadersGameState gameState) {
        component.add(LayoutUtils.buttonOf("New Game", () -> gameState.reset()));
    }

    @Override
    public JComponent getComponent() { return component; }
}
