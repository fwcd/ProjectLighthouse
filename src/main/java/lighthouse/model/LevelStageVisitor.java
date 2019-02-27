package lighthouse.model;

/**
 * Defined external, polymorphic behavior
 * based on a game stage.
 */
public interface LevelStageVisitor<T> {
	T visitStart(LevelStages.Start stage);
	
	T visitInGame(LevelStages.InGame stage);
	
	T visitGoal(LevelStages.Goal stage);
}
