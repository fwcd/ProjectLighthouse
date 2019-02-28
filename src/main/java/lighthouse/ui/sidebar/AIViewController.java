package lighthouse.ui.sidebar;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.spinner.WebSpinner;

import lighthouse.ai.AIMain;
import lighthouse.model.AppModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIViewController implements ViewController {
	private static int threadIndex = 0;
	private final AppModel appModel;
	private final JPanel component;
	private final WebProgressBar progressBar;
	private Thread thread;
	
	public AIViewController(AppModel appModel) {
		this.appModel = appModel;
		
		WebSpinner populationSize = new WebSpinner(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
		WebSpinner iterations = new WebSpinner(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
		
		progressBar = new WebProgressBar();		
		component = LayoutUtils.vboxOf(
			LayoutUtils.compoundOf(
				new JLabel("Population size:"),
				populationSize
			),
			LayoutUtils.compoundOf(
				new JLabel("Iterations:"),
				iterations
			),
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Train Population", () -> train((int) populationSize.getValue(), (int) iterations.getValue())),
				LayoutUtils.buttonOf("Stop", () -> stopTraining())
			),
			progressBar
		);
	}
	
	private void stopTraining() {
		if (thread != null) {
			thread.interrupt();
		}
	}
	
	private void train(int populationSize, int iterations) {
		stopTraining();
		thread = new Thread(() -> {
			AIMain ai = new AIMain(populationSize);
			appModel.setAI(ai);
			progressBar.setMinimum(0);
			progressBar.setMaximum(iterations);
			
			for (int i = 0; i < iterations && !Thread.interrupted(); i++) {
				ai.train(appModel.getGameState().getLevel());
				int index = i;
				SwingUtilities.invokeLater(() -> progressBar.setValue(index));
			}
		}, "AI" + threadIndex);
		threadIndex++;
		thread.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
