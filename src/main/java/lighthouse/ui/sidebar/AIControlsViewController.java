package lighthouse.ui.sidebar;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.spinner.WebSpinner;

import lighthouse.ai.AIMain;
import lighthouse.model.AppModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIControlsViewController implements ViewController {
	private final AppModel appModel;
	private final JPanel component;
	private final WebProgressBar progressBar;
	
	public AIControlsViewController(AppModel appModel) {
		this.appModel = appModel;
		
		WebSpinner populationSize = new WebSpinner(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
		WebSpinner iterations = new WebSpinner(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
		
		progressBar = new WebProgressBar();
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(new FlowLayout(FlowLayout.LEADING, 2, 0),
				new JLabel("Population size:"),
				populationSize
			),
			LayoutUtils.panelOf(new FlowLayout(FlowLayout.LEADING, 2, 0),
				new JLabel("Iterations:"),
				iterations
			),
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Train Population", () -> train((int) populationSize.getValue(), (int) iterations.getValue()))
			),
			progressBar
		);
	}
	
	private void train(int populationSize, int iterations) {
		new Thread(() -> {
			AIMain ai = new AIMain(populationSize);
			appModel.setAI(ai);
			progressBar.setMinimum(0);
			progressBar.setMaximum(iterations);
			
			for (int i = 0; i < iterations; i++) {
				ai.train(appModel.getGameState().getLevel());
				progressBar.setValue(i);
			}
		}, "AI training").start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
