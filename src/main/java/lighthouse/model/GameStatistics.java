package lighthouse.model;

import lighthouse.util.ListenerList;

/**
 * POJO containing some statistics about the game.
 */
public class GameStatistics {
	private int moveCount = 0;
	private double estimatedDistanceToGoal = 0;
	
	private final ListenerList<Integer> moveCountListeners = new ListenerList<>();
	private final ListenerList<Double> distanceToGoalListeners = new ListenerList<>();
	
	public int getMoveCount() { return moveCount; }
	
	public double getEstimatedDistanceToGoal() { return estimatedDistanceToGoal; }
	
	public void reset() {
		moveCount = 0;
		estimatedDistanceToGoal = 0;
		moveCountListeners.fire(moveCount);
		distanceToGoalListeners.fire(estimatedDistanceToGoal);
	}
	
	public void incrementMoveCount() {
		moveCount++;
		moveCountListeners.fire(moveCount);
	}
	
	public void setEstimatedDistanceToGoal(double estimatedDistanceToGoal) {
		this.estimatedDistanceToGoal = estimatedDistanceToGoal;
		distanceToGoalListeners.fire(estimatedDistanceToGoal);
	}
	
	public ListenerList<Integer> getMoveCountListeners() { return moveCountListeners; }
	
	public ListenerList<Double> getDistanceToGoalListeners() { return distanceToGoalListeners; }
}
