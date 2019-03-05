package lighthouse.model;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final FileSaveState saveState = new FileSaveState();
	private final GameState activeGameState = null;
	
	public GameState getActiveGameState() { return activeGameState; }
	
	public FileSaveState getSaveState() { return saveState; }
}
