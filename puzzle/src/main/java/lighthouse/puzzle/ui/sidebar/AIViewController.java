package lighthouse.puzzle.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.spinner.WebSpinner;

import lighthouse.puzzle.ai.PuzzleAI;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIViewController implements SwingViewController {
	private static int threadIndex = 0;
	private final PuzzleGameState gameState;
	private final JPanel component;
	private final WebProgressBar progressBar;
	private Thread thread;
	
	public AIViewController(PuzzleGameState gameState) {
		this.gameState = gameState;
		
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
			PuzzleAI ai = new PuzzleAI(populationSize);
			progressBar.setMinimum(0);
			progressBar.setMaximum(iterations);
			
			for (int i = 0; i < iterations && !Thread.interrupted(); i++) {
				ai.train(gameState.getLevel());
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
