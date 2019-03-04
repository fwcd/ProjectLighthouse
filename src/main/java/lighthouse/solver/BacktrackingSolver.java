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
                    .filter(move -> {
                        Board child = tmpCurrent.childBoard(move);
                        return !forbidden.contains(child) && toSolve.isAllowed(child);
                    }).findFirst();
            
            if (!nextMove.isPresent()){
                moves.remove(moves.size()-1);
                current = moves.get(moves.size()-1);
                continue;
            }
            current.perform(nextMove.orElse(null));
            moves.add(current.copy());
        }
        LOG.info("Starting optimization with: {} moves", moves.size());
        while (true){
            
            int skips = 0;
            int begin = 0;
            for (int start = 0; start < moves.size(); start++){
                Board startBoard = moves.get(start);
                Iterator<Move> iter = startBoard.streamPossibleMoves().iterator();
                while(iter.hasNext()){
                    Move move = iter.next();
                    int distance = moves.indexOf(startBoard.childBoard(move)) - start;
                    if(distance > skips){
                        begin = start + 1;
                        skips = distance - 1;
                    }
                }
            }
            
            LOG.info("skipping: {} from {}", skips, begin);
            for(int i = 0; i < skips; i++){
                moves.remove(begin);
            }
            if(skips <= 1 || begin >= moves.size()){
                break;
            }
            
        }
        
        LOG.info("Finished optimization with {} moves", moves.size());
        LOG.info("Done");
        return moves;
    }
}
