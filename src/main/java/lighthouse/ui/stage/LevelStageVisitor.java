package lighthouse.ui.stage;

/**
 * Defined external, polymorphic behavior
 * based on a game stage.
 */
public interface LevelStageVisitor<T> {
	T visitStart(LevelStages.Start stage);
	
	T visitCurrent(LevelStages.Current stage);
	
	T visitGoal(LevelStages.Goal stage);
}
