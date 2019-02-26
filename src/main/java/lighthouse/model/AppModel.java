package lighthouse.model;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final Game game = new Game();
	private final FileSaveState saveState = new FileSaveState();
	
	public Game getGame() { return game; }
	
	public FileSaveState getSaveState() { return saveState; }
}
