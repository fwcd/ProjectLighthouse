package lighthouse.model;

/**
 * Represents a "state" of the game in a level,
 * like "at the beginning" or "at the end".
 */
public interface GameStage extends Comparable<GameStage> {
	int getIndex();
	
	String getName();
	
	@Override
	default int compareTo(GameStage o) { return Integer.compare(getIndex(), o.getIndex()); }
}
