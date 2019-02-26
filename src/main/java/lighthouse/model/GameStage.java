package lighthouse.model;

import java.util.Optional;

/**
 * Represents a "state" of the game in a level,
 * like "at the beginning" or "at the end".
 */
public interface GameStage extends Comparable<GameStage> {
	int getIndex();
	
	String getName();
	
	void transitionFrom(GameStage lastStage, Game game);
	
	Optional<Board> getBoardFrom(Game game);
	
	default boolean isCurrent() { return false; }
	
	@Override
	default int compareTo(GameStage o) { return Integer.compare(getIndex(), o.getIndex()); }
}
