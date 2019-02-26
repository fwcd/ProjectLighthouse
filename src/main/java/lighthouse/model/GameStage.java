package lighthouse.model;

import java.util.Optional;

/**
 * Represents a stage in the game such
 * as the "beginning" or the "end". This is
 * useful, since the game naturally requires
 * some differentiation between start and end states.
 */
public interface GameStage extends Comparable<GameStage> {
	int getIndex();
	
	String getName();
	
	void transitionFrom(GameStage lastStage, GameState game);
	
	Optional<Board> getBoardFrom(GameState game);
	
	<T> T accept(GameStageVisitor<T> visitor);
	
	default boolean isCurrent() { return false; }
	
	@Override
	default int compareTo(GameStage o) { return Integer.compare(getIndex(), o.getIndex()); }
}
