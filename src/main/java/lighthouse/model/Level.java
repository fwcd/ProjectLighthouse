package lighthouse.model;

import java.io.Serializable;

/**
 * A game level consisting of a start
 * board and a goal.
 */
public class Level implements Serializable {
	private static final long serialVersionUID = -4247084042893031489L;
	private Board start;
	private Board goal;
	
	/** Creates a new level with empty start/goal boards. */
	public Level() {
		start = new Board();
		goal = new Board();
	}
	
	public Level(Board start, Board goal) {
		this.start = start;
		this.goal = goal;
	}
	
	public boolean isTooEasy() { return start.equals(goal); }
	
	public Board getStart() { return start; }
	
	public Board getGoal() { return goal; }
	
	public boolean isCompleted(Board tested) { return tested.equals(goal); }
}
