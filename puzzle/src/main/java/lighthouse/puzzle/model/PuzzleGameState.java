package lighthouse.puzzle.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.GameState;
import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * Holds the current game state which includes the actively manipulated board,
 * the current level and more.
 */
public class PuzzleGameState implements GameState {
    private static final Logger LOG = LoggerFactory.getLogger(PuzzleGameState.class);
    private static final Gson GSON = new Gson();
    
    /** The in-game board. */
    private Board board;
    /** The level played. */
    private Level level;
	
    private final ListenerList<Level> levelListeners = new ListenerList<>("PuzzleGameState.levelListeners");
    private final ListenerList<Board> boardListeners = new ListenerList<>("PuzzleGameState.boardListeners");
    private final ListenerList<Void> changeListeners = new ListenerList<>("PuzzleGameState.changeListeners");
    
    public PuzzleGameState() {
        board = new Board();
        level = new Level();
    }
    
    public PuzzleGameState(Board initialBoard, Level level) {
        board = initialBoard;
        this.level = level;
    }
    
    public boolean isWon() {
        return board.equals(level.getGoal());
    }
    
    public void startLevel() {
        setBoard(level.getStart().copy());
    }
    
    public void setBoard(Board board) {
        if (this.board != null) {
            this.board.getChangeListeners().remove(changeListeners);
        }
        this.board = board;
        boardListeners.fire(board);
        board.getChangeListeners().add(changeListeners);
    }
    
    @Override
	public void saveLevelTo(Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            LOG.info("Saving level to {}...", path);
            GSON.toJson(level, writer);
		}
	}
    
    @Override
	public void loadLevelFrom(Path path) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
            LOG.info("Loading level from {}...", path);
            setLevel(GSON.fromJson(reader, Level.class));
		}
    }
    
    @Override
    public void loadLevelFrom(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            LOG.info("Loading level from stream...");
            setLevel(GSON.fromJson(reader, Level.class));
		}
    }
    
    @Override
    public IntVec getGridSize() {
        return board.getSize();
    }
    
    public void setLevel(Level level) {
        if (this.level != null) {
            this.level.getStart().getChangeListeners().remove(changeListeners);
            this.level.getGoal().getChangeListeners().remove(changeListeners);
        }
        this.level = level;
        levelListeners.fire(level);
        level.getStart().getChangeListeners().add(changeListeners);
        level.getGoal().getChangeListeners().add(changeListeners);
    }
	
	public Board getBoard() { return board; }
	
	public Level getLevel() { return level; }
	
	public ListenerList<Level> getLevelListeners() { return levelListeners; }
	
    public ListenerList<Board> getBoardListeners() { return boardListeners; }
    
    public ListenerList<Void> getChangeListeners() { return changeListeners; }
    
    public PuzzleGameState withCopiedBoard() { return new PuzzleGameState(board.copy(), level); }
}
