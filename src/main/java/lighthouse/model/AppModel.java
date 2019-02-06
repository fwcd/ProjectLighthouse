package lighthouse.model;

/**
 * The application's model.
 */
public class AppModel {
	private final GameBoard board = new GameBoard(4, 6);
	
	public GameBoard getBoard() {
		return board;
	}
}
