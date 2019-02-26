package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lighthouse.model.Game;
import lighthouse.model.GameStage;
import lighthouse.ui.ViewController;

public class GameStageIconViewController implements ViewController {
	private final JComponent component;
	
	public GameStageIconViewController(GameStage stage, Game game) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		// TODO: Render a miniature version of the level
		component.add(new JLabel(stage.getName()));
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
