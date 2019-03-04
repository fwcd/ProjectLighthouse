package lighthouse.ui.sidebar;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.solver.BacktrackingSolver;
import lighthouse.solver.Solver;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.IDGenerator;

public class SolverViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(SolverViewController.class);
	private final JPanel component;
	private final BoardViewController board;
	private final GameState gameState;
	private Thread solverThread = null;
	
	public SolverViewController(GameState gameState, BoardViewController board) {
		this.gameState = gameState;
		this.board = board;
		
		WebSpinner playbackSpeed = new WebSpinner(new SpinnerNumberModel(4, 1, 1000, 1));
		
		component = LayoutUtils.vboxOf(
			LayoutUtils.compoundOf(
				new JLabel("Playback speed (boards/sec): "),
				playbackSpeed
			),
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Solve with Backtracking", () -> solveWithBacktracking((int) playbackSpeed.getValue())),
				LayoutUtils.buttonOf("Stop", this::stop)
			)
		);
	}
	
	private void stop() {
		if (solverThread != null) {
			solverThread.interrupt();
		}
	}
	
	private void solveWithBacktracking(int boardsPerSecond) {
		stop();
		solverThread = new Thread(() -> {
			Solver solver = new BacktrackingSolver();
			Level level = gameState.getLevel();
			try {
				List<Board> solution = solver.solve(level);
				board.play(solution, 1000 / boardsPerSecond);
			} catch (Exception e) {
				LOG.error("An error occurred while solving:", e);
			}
		}, "Solver " + IDGenerator.INSTANCE.nextID());
		solverThread.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
