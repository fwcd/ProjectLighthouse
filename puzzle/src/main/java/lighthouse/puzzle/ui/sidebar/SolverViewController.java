package lighthouse.puzzle.ui.sidebar;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.solver.BacktrackingSolver;
import lighthouse.puzzle.solver.Solver;
import lighthouse.puzzle.ui.board.BoardViewController;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.viewmodel.graphics.ConfettiAnimation;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.IDGenerator;

public class SolverViewController implements SwingViewController {
	private static final Logger LOG = LoggerFactory.getLogger(SolverViewController.class);
	private final JPanel component;
	private final BoardViewController board;
	private final PuzzleGameState gameState;
	private Thread solverThread = null;
	
	public SolverViewController(PuzzleGameState gameState, BoardViewController board) {
		this.gameState = gameState;
		this.board = board;
		
		WebSpinner playbackSpeed = new WebSpinner(new SpinnerNumberModel(4, 1, 1000, 1));
		
		// TODO: Integrate AI controls into solvers
		// AIViewController aiControls = new AIViewController(model);
		// accordion.addPane("AI Controls", aiControls.getComponent()).collapse();
		
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
				board.play(solution, 1000 / boardsPerSecond)
					.thenRun(() -> board.play(new ConfettiAnimation()));
			} catch (Exception e) {
				LOG.error("An error occurred while solving:", e);
			}
		}, "Solver " + IDGenerator.INSTANCE.nextID());
		solverThread.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
