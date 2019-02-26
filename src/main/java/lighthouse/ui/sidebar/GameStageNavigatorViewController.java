package lighthouse.ui.sidebar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.model.Game;
import lighthouse.model.GameStage;
import lighthouse.model.GameStages;

public class GameStageNavigatorViewController {
	private final WebStepProgress component;
	
	public GameStageNavigatorViewController(Game game) {
		component = new WebStepProgress();
		component.addSteps(GameStages.STAGES.stream().sorted().map(GameStage::getName).toArray(String[]::new));
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				GameStages.STAGES.get(component.getSelectedStepIndex()).navigateToIn(game);
			}
		});
	}
	
	public JComponent getComponent() { return component; }
}
