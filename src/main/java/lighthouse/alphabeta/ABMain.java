package lighthouse.alphabeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lighthouse.model.Board;
import lighthouse.model.Level;
import lighthouse.model.Move;

public abstract class ABMain {

    public static List<Board> solve(Level toSolve) {
        List<Board> moves = new ArrayList<>();
        Board current = toSolve.getStart();
        Board goal = toSolve.getGoal();
        List<Board> forbidden = new ArrayList<>();
        while (!current.equals(goal)) {
            Board tmpCurrent = current;
            Optional<Move> nextMove = current.streamPossibleMoves()
                    .filter(move -> !forbidden.contains(tmpCurrent.childBoard(move))).findFirst();
            if (!nextMove.isPresent()){
                moves.remove(moves.size()-1);
                current = moves.get(moves.size()-1);
                break;
            }
            current.perform(nextMove.orElse(null));
            moves.add(current.copy());
            forbidden.add(current.copy());
        }
        return moves;
    }

}
