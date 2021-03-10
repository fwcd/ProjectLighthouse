package lighthouse.snake.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.snake.model.SnakeGameState;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.util.LayoutUtils;

public class SnakeControlsViewController implements SwingViewController {
    private final JPanel component = new JPanel();

    public SnakeControlsViewController(SnakeGameState gameState) {
        component.add(LayoutUtils.buttonOf("New Game", gameState::reset));
    }

    @Override
    public JComponent getComponent() { return component; }
}
