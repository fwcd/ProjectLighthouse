package lighthouse.model;

import java.util.Optional;

/**
 * Represents a stage in the game such
 * as the "beginning" or the "end". This is
 * useful, since the game naturally requires
 * some differentiation between start and end states.
 */
public interface LevelStage extends Comparable<LevelStage> {
	int getIndex();
	
	String getName();
	
	void transitionFrom(LevelStage lastStage, GameState game);
	
	Optional<Board> getBoardFrom(GameState game);
	
	<T> T accept(LevelStageVisitor<T> visitor);
	
	default boolean isInGame() { return false; }
	
	@Override
	default int compareTo(LevelStage o) { return Integer.compare(getIndex(), o.getIndex()); }
}
