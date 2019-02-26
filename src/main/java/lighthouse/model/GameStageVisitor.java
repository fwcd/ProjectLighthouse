package lighthouse.model;

/**
 * Defined external, polymorphic behavior
 * based on a game stage.
 */
public interface GameStageVisitor<T> {
	T visitStart(GameStages.Start stage);
	
	T visitCurrent(GameStages.Current stage);
	
	T visitGoal(GameStages.Goal stage);
}
