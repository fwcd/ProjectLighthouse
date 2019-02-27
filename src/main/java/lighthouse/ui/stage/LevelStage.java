package lighthouse.ui.stage;

import java.util.Optional;

import lighthouse.model.Board;
import lighthouse.model.GameState;

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
	
	default boolean isCurrent() { return false; }
	
	@Override
	default int compareTo(LevelStage o) { return Integer.compare(getIndex(), o.getIndex()); }
}
