package lighthouse.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.util.ListenerList;

/**
 * Holds the current game state which includes the actively manipulated board,
 * the current level and more.
 */
public class GamePlayingState {
    private static final Logger LOG = LoggerFactory.getLogger(GamePlayingState.class);
	private static final Gson GSON = new Gson();
    
    /** The active board. Usually displayed to the user. */
    private Board activeBoard = new Board();
    /** The level played. */
    private Level level = new Level();
    /**
     * A "backup" of the current in game board in case the
     * user looks at the start or end board of the level.
     */
    private Board storedInGameBoard;
	
    private final ListenerList<Level> levelListeners = new ListenerList<>();
	private final ListenerList<Board> boardListeners = new ListenerList<>();
    
    public void storeInGameBoard() { storedInGameBoard = activeBoard.copy(); }
    
    public boolean isWon() {
        return activeBoard.equals(level.getGoal());
    }
    
    public void revertToInGameBoardOr(Supplier<Board> otherwise) {
        if (storedInGameBoard == null) {
            setBoard(otherwise.get());
        } else {
            setBoard(storedInGameBoard.copy());
        }
    }
    
    public void setBoard(Board board) {
        this.activeBoard = board;
        boardListeners.fire(board);
    }
	
	/** Saves a level as JSON to a file. */
	public void saveLevelTo(Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            LOG.info("Saving level to {}...", path);
            GSON.toJson(level, writer);
		}
	}
	
	/** Loads a level from a JSON file. */
	public void loadLevelFrom(Path path) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
            LOG.info("Loading level from {}...", path);
            level = GSON.fromJson(reader, Level.class);
			levelListeners.fire(level);
		}
    }
	
	public Board getActiveBoard() { return activeBoard; }
	
	public Level getLevel() { return level; }
	
	public ListenerList<Level> getLevelListeners() { return levelListeners; }
	
	public ListenerList<Board> getBoardListeners() { return boardListeners; }
}
