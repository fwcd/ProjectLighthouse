package lighthouse.model;

/**
 * Stores information about a current "Schimmler" game match.
 */
public class GameMatch {
    private final Board start;
    private final Board goal;
    private final Board current;

    private boolean won = false;

    public GameMatch(Board start, Board goal) {
        this.start = start;
        this.goal = goal;
        current = start.copy();
    }

    public void tick() {
        if (current.equals(goal)) {
            won = true;
        }
    }
    
    public Board getStart() { return start; }
    
    public Board getGoal() { return goal; }
    
    public Board getCurrent() { return current; }
    
    public boolean isWon() { return won; }
}
