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
	
	/** Deserialization constructor. */
	protected Level() {}
	
	public Level(Board start, Board goal) {
		this.start = start;
		this.goal = goal;
	}
	
	public Board getStart() { return start; }
	
	public Board getGoal() { return goal; }
}
