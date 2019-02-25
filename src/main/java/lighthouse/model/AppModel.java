package lighthouse.model;


/**
 * The application's model. Contains the game board.
 */
public class AppModel {
	private final Board board = new Board(4, 6);
	
	public Board getBoard() {
		return board;
	}
}
