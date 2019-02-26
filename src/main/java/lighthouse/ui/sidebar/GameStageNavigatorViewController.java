package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.model.GameStage;
import lighthouse.model.GameStages;

public class GameStageNavigatorViewController {
	private final WebStepProgress component;
	
	public GameStageNavigatorViewController() {
		component = new WebStepProgress();
		component.addSteps(GameStages.STAGES.stream().sorted().map(GameStage::getName).toArray(String[]::new));
	}
	
	public JComponent getComponent() { return component; }
}
