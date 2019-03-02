package lighthouse.alphabeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import lighthouse.model.Board;
import lighthouse.model.Level;
import lighthouse.model.Move;

public class AlphaBeta {

    public static List<Board> solve(Level toSolve) {
        List<Board> moves = new ArrayList<>();
        Board current = toSolve.getStart().copy();
        Board goal = toSolve.getGoal();
        List<Board> forbidden = new ArrayList<>();
        forbidden.add(current.copy());
        while (!current.equals(goal)) {
            Board tmpCurrent = current.copy();
            Optional<Move> nextMove = current.streamPossibleMoves()
                    .filter(move -> !forbidden.contains(tmpCurrent.childBoard(move))).findFirst();
            Iterator<Move> iter = current.streamPossibleMoves().iterator();

            while(iter.hasNext()){
                System.out.println(iter.next());
            }
            
            if (!nextMove.isPresent()){
                System.out.println("Reverting");
                moves.remove(moves.size()-1);
                current = moves.get(moves.size()-1);
                continue;
            }
            System.out.println("Moving " + nextMove.orElse(null));
            current.perform(nextMove.orElse(null));
            moves.add(current.copy());
            forbidden.add(current.copy());
        }
        System.out.println("Done");
        return moves;
    }

}
