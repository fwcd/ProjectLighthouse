package lighthouse.ui.sidebar;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

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

public class SolverViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(SolverViewController.class);
	private final JPanel component;
	private final BoardViewController board;
	private final GameState gameState;
	
	public SolverViewController(GameState gameState, BoardViewController board) {
		this.gameState = gameState;
		this.board = board;
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Solve with Backtracking", this::backtrackingSolve)
			)
		);
	}
	
	private void backtrackingSolve() {
		new Thread(() -> {
			Solver solver = new BacktrackingSolver();
			Level level = gameState.getLevel();
			try {
				List<Board> solution = solver.solve(level);
				board.play(solution, 100);
			} catch (Exception e) {
				LOG.error("An error occurred while solving:", e);
			}
		}, "Solver").start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
