package lighthouse.puzzle.solver;

import java.util.List;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;

public interface Solver {
	/** Finds the winning sequence of boards. */
	List<Board> solve(Level toSolve);
}
