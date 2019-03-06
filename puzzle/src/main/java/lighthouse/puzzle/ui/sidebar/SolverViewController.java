package lighthouse.puzzle.ui.sidebar;

import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import com.alee.laf.spinner.WebSpinner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.solver.BacktrackingSolver;
import lighthouse.puzzle.solver.RecursiveSolver;
import lighthouse.puzzle.solver.Solver;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.viewmodel.graphics.ConfettiAnimation;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.IDGenerator;

public class SolverViewController implements SwingViewController {
	private static final Logger LOG = LoggerFactory.getLogger(SolverViewController.class);
	private final JPanel component;
	private final SceneInteractionFacade interactionFacade;
	private final BoardViewModel board;
	private final PuzzleGameState gameState;
	private Thread solverThread = null;
	
	public SolverViewController(PuzzleGameState gameState, BoardViewModel board, SceneInteractionFacade interactionFacade) {
		this.gameState = gameState;
		this.board = board;
		this.interactionFacade = interactionFacade;
		
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
				LayoutUtils.buttonOf("Backtrack", () -> solveWith(new BacktrackingSolver(), (int) playbackSpeed.getValue())),
				LayoutUtils.buttonOf("Recursive solve", () -> solveWith(new RecursiveSolver(), (int) playbackSpeed.getValue())),
				LayoutUtils.buttonOf("Stop", this::stop)
			)
		);
	}
	
	private void stop() {
		if (solverThread != null) {
			solverThread.interrupt();
		}
	}
	
	private void solveWith(Solver solver, int boardsPerSecond) {
		stop();
		solverThread = new Thread(() -> {
			Level level = gameState.getLevel();
			try {
				Iterator<Board> solution = solver.solve(level).iterator();
				Timer timer = new Timer(1000 / boardsPerSecond, e -> {
					if (solution.hasNext()) {
						board.transitionTo(solution.next());
						board.getStatistics().incrementMoveCount();
						interactionFacade.update();
					} else {
						((Timer) e.getSource()).stop();
						interactionFacade.play(new ConfettiAnimation());
					}
				});
				
				timer.setRepeats(true);
				timer.start();
			} catch (Exception e) {
				LOG.error("An error occurred while solving:", e);
			}
		}, "Solver " + IDGenerator.INSTANCE.nextID());
		solverThread.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
