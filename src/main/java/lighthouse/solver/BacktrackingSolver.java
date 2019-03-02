package lighthouse.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Level;
import lighthouse.model.Move;

public class BacktrackingSolver implements Solver {
    private static final Logger LOG = LoggerFactory.getLogger(BacktrackingSolver.class);
    
    @Override
    public List<Board> solve(Level toSolve) {
        List<Board> moves = new ArrayList<>();
        Board current = toSolve.getStart().copy();
        Board goal = toSolve.getGoal();
        List<Board> forbidden = new ArrayList<>();
        moves.add(current.copy());
        while (!current.equals(goal)) {
            forbidden.add(current.copy());
            Board tmpCurrent = current.copy();
            Optional<Move> nextMove = current.streamPossibleMoves()
                    .filter(move -> !forbidden.contains(tmpCurrent.childBoard(move))).findFirst();
            
            if (!nextMove.isPresent()){
                moves.remove(moves.size()-1);
                current = moves.get(moves.size()-1);
                continue;
            }
            current.perform(nextMove.orElse(null));
            moves.add(current.copy());
        }
        LOG.info("Done");
        return moves;
    }
}
