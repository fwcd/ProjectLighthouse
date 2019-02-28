package lighthouse.model;

import lighthouse.util.ListenerList;

/**
 * POJO containing some statistics about the game.
 */
public class GameStatistics {
	private int moveCount = 0;
	private int estimatedDistanceToGoal = 0;
	
	private final ListenerList<Integer> moveCountListeners = new ListenerList<>();
	private final ListenerList<Integer> distanceToGoalListeners = new ListenerList<>();
	
	public int getMoveCount() { return moveCount; }
	
	public int getEstimatedDistanceToGoal() { return estimatedDistanceToGoal; }
	
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
		moveCountListeners.fire(moveCount);
	}
	
	public void setEstimatedDistanceToGoal(int estimatedDistanceToGoal) {
		this.estimatedDistanceToGoal = estimatedDistanceToGoal;
		distanceToGoalListeners.fire(estimatedDistanceToGoal);
	}
	
	public ListenerList<Integer> getMoveCountListeners() { return moveCountListeners; }
	
	public ListenerList<Integer> getDistanceToGoalListeners() { return distanceToGoalListeners; }
}
