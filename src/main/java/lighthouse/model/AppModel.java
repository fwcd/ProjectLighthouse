package lighthouse.model;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final GameState gameState = new GameState();
	private final FileSaveState saveState = new FileSaveState();
	
	public GameState getGameState() { return gameState; }
	
	public FileSaveState getSaveState() { return saveState; }
}
