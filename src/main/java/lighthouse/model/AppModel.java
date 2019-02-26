package lighthouse.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import lighthouse.util.ListenerList;

/**
 * The application's model. Contains the game board.
 */
public class AppModel {
	private static final Gson GSON = new Gson();
	private final FileSaveState saveState = new FileSaveState();
	private Board board = new Board(4, 6);
	
	private final ListenerList<Board> boardListeners = new ListenerList<>();
	
	/** Saves a board as JSON to a file. */
	public void saveBoardTo(Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			GSON.toJson(board, writer);
		}
	}
	
	/** Loads a board from a JSON file. */
	public void loadBoardFrom(Path path) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			board = GSON.fromJson(reader, Board.class);
			boardListeners.fire(board);
		}
	}
	
	public Board getBoard() { return board; }
	
	public FileSaveState getSaveState() { return saveState; }
	
	public ListenerList<Board> getBoardListeners() { return boardListeners; }
}
