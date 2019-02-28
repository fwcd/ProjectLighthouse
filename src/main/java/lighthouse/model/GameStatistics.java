package lighthouse.model;

/**
 * POJO containing some statistics
 * about the game.
 */
public class GameStatistics {
	private int playerMoveCount = 0;
	private int estimatedDistanceToGoal = 0;
	
	public int getPlayerMoveCount() { return playerMoveCount; }
	
	public int getEstimatedDistanceToGoal() { return estimatedDistanceToGoal; }
	
	public void setPlayerMoveCount(int playerMoveCount) { this.playerMoveCount = playerMoveCount; }
	
	public void setEstimatedDistanceToGoal(int estimatedDistanceToGoal) { this.estimatedDistanceToGoal = estimatedDistanceToGoal; }
}
