package lighthouse.puzzle.solver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.model.Move;

public class BacktrackingSolver implements Solver {
    private static final Logger LOG = LoggerFactory.getLogger(BacktrackingSolver.class);
    
    @Override
    public List<Board> solve(Level toSolve) {
        LOG.info("Starting solver");
        
        Deque<Board> moves = new ArrayDeque<>();
        Board current = toSolve.getStart().copy();
        Board goal = toSolve.getGoal();
        Set<Board> forbidden = new HashSet<>();

        moves.add(current.copy());
        while (!current.equals(goal)) {
            // Stop the solver if the thread was interrupted
            if (Thread.interrupted()) {
                LOG.info("Stopped solver");
                return Collections.emptyList();
            }
            
            forbidden.add(current.copy());
            Board tmpCurrent = current.copy();

            Optional<Move> nextMove = current.streamPossibleMoves()
                    .filter(move -> {
                        Board child = tmpCurrent.childBoard(move);
                        return !forbidden.contains(child) && toSolve.isAllowed(child);
                    }).findFirst();
            
            if (!nextMove.isPresent()){
                moves.removeLast();
                current = moves.peekLast();
                continue;
            }
            current.perform(nextMove.orElse(null));
            moves.add(current.copy());
        }
        
        List<Board> finalMoves = new ArrayList<>();
        LOG.info("Starting optimization with: {} moves", moves.size());
        while (!moves.isEmpty()) {
            // Stop the solver if the thread was interrupted
            if (Thread.interrupted()) {
                LOG.info("Stopped solver while optimizing");
                return Collections.emptyList();
            }
            
            Board board = moves.removeFirst();
            Iterator<Board> iter = moves.iterator();
            int skips = 0;
            while (iter.hasNext()) {
                Board futureBoard = iter.next();
                boolean isLastBoard = skips == moves.size() - 1;

                if (!isLastBoard && board.equals(futureBoard)) {
                    LOG.info("Skipping {} boards", skips);
                    for (int i = 0; i < skips; i++) {
                        moves.removeFirst();
                    }
                }

                skips++;
            }
            
            finalMoves.add(board);
        }
        
        LOG.info("Finished optimization with {} moves", finalMoves.size());
        LOG.info("Done");
        return finalMoves;
    }
}
