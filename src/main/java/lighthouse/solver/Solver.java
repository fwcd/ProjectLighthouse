package lighthouse.solver;

import java.util.List;

import lighthouse.model.Board;
import lighthouse.model.Level;

public interface Solver {
	/** Finds the winning sequence of boards. */
	List<Board> solve(Level toSolve);
}
