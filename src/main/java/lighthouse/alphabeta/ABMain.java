package lighthouse.alphabeta;

import java.util.ArrayList;
import java.util.Optional;

import lighthouse.model.Board;
import lighthouse.model.Level;
import lighthouse.model.Move;

public abstract class ABMain {

    public static ArrayList<Board> solve(Level toSolve) {
        ArrayList<Board> moves = new ArrayList<>();
        Board current = toSolve.getStart();
        Board goal = toSolve.getGoal();
        ArrayList<Board> forbidden = new ArrayList<>();
        while (!current.equals(goal)) {
            Optional<Move> nextMove = current.streamPossibleMoves()
                    .filter(move -> !forbidden.contains(current.childBoard(move))).findFirst();
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