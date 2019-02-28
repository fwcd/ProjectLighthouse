package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.spinner.WebSpinner;

import lighthouse.ai.AIMain;
import lighthouse.model.AppModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIControlsViewController implements ViewController {
	private final AppModel appModel;
	private final JComponent component;
	private final WebProgressBar progressBar;
	
	public AIControlsViewController(AppModel appModel) {
		this.appModel = appModel;
		WebSpinner spinner = new WebSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		progressBar = new WebProgressBar();
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(
				spinner,
				LayoutUtils.buttonOf("Train Population", () -> {}),
				progressBar
			)
		);
	}
	
	private void train(int populationSize) {
		AIMain ai = new AIMain(populationSize);
		appModel.setAI(ai);
		ai.train(appModel.getGameState().getLevel());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
