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
 * Holds the current board and the level.
 */
public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);
	private static final Gson GSON = new Gson();
    private Board board;
    private Level level;
    
    private Board backup;
	private GameStage selectedStage = GameStages.CURRENT;
	private Status status;
	
	private final ListenerList<GameStage> stageListeners = new ListenerList<>();
    private final ListenerList<Level> levelListeners = new ListenerList<>();
    private final ListenerList<Board> boardListeners = new ListenerList<>();
	private final ListenerList<Status> statusListeners = new ListenerList<>();

    private boolean won = false;

    public Game() {
        level = new Level();
        board = new Board();
    }
    
    public Game(Level level, Board board) {
        this.level = level;
        this.board = board;
    }

    public void tick() {
        if (level.isCompleted(board)) {
            LOG.info("Completed level");
            won = true;
        }
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
    
    public Level getLevel() { return level; }
    
    public Board getBoard() { return board; }
    
    public void setBoard(Board board) {
        this.board = board;
        boardListeners.fire(board);
    }
    
    public void revertToBackupBoardOr(Supplier<Board> otherwise) {
        if (backup == null) {
            setBoard(otherwise.get());
        } else {
            setBoard(backup.copy());
        }
    }
	
	public GameStage getSelectedStage() { return selectedStage; }
	
	public void selectStage(GameStage stage) {
		selectedStage = stage;
		stageListeners.fire(stage);
	}
    
    public void backupBoard() { backup = board.copy(); }
    
    public boolean isWon() { return won; }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
    
    public ListenerList<Level> getLevelListeners() { return levelListeners; }
    
    public ListenerList<Board> getBoardListeners() { return boardListeners; }
	
	public ListenerList<GameStage> getStageListeners() { return stageListeners; }
}
