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
	
	/**
	 * Fetches the target brick for a brick from the starting board.
	 * @throws IllegalStateException if the brick could not be found
	 */
	public Brick goalBrickFor(Brick startBrick) {
		return goal.streamBricks()
			.filter(startBrick::matchesIDOf)
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Goal board does not contain " + startBrick + " from start board!"));
	}
	
	/**
	 * Fetches the starting brick for a brick for the target/goal board.
	 * @throws IllegalStateException if the brick could not be found
	 */
	public Brick startBrickFor(Brick goalBrick) {
		return start.streamBricks()
			.filter(goalBrick::matchesIDOf)
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Start board does not contain " + goalBrick + " from goal board!"));
	}
}
