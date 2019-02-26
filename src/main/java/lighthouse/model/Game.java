package lighthouse.model;

/**
 * Holds the current board and the level.
 */
public class Game {
    private final Level level;
    private final Board board;

    private boolean won = false;

    public Game() {
        level = null;
        board = new Board(4, 6);
    }
    
    public Game(Level level, Board board) {
        this.level = level;
        this.board = board;
    }

    public void tick() {
        if (level.isCompleted(board)) {
            won = true;
        }
    }
    
    public Level getLevel() { return level; }
    
    public Board getBoard() { return board; }
    
    public boolean isWon() { return won; }
}
