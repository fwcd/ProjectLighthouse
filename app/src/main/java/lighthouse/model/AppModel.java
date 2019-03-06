package lighthouse.model;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final FileSaveState saveState = new FileSaveState();
	private GameState activeGameState = null;
	
	public void setActiveGameState(GameState activeGameState) { this.activeGameState = activeGameState; }
	
	public GameState getActiveGameState() { return activeGameState; }
	
	public FileSaveState getSaveState() { return saveState; }
}
