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
	 * Fetches the target brick for a brick.
	 * @throws IllegalStateException if the brick could not be found
	 */
	public Brick goalBrickFor(Brick brick) {
		return goal.streamBricks()
			.filter(brick::matchesIDOf)
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Goal board does not contain " + brick + " from start board!"));
	}
	
	/**
	 * Fetches the starting brick for a brick.
	 * @throws IllegalStateException if the brick could not be found
	 */
	public Brick startBrickFor(Brick brick) {
		return start.streamBricks()
			.filter(brick::matchesIDOf)
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Start board does not contain " + brick + " from goal board!"));
	}
	
	/** Fetches the average distance to the goal state from the given board. */
	public double estimatedDistanceToGoal(Board board) {
		return board.streamBricks()
			.mapToDouble(brick -> goalBrickFor(brick).getPos().sub(brick.getPos()).length())
			.average()
			.orElse(Double.NaN);
	}
}
