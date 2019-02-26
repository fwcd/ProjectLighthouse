package lighthouse.model;


/**
 * The application's model. Contains the game board.
 */
public class AppModel {
	private final Board board = new Board(4, 6);
	private final FileSaveState saveState = new FileSaveState();
	
	public Board getBoard() { return board; }
	
	public FileSaveState getSaveState() { return saveState; }
}
