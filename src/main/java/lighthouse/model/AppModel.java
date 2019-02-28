package lighthouse.model;

import lighthouse.ai.AIMain;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final GameState gameState = new GameState();
	private final FileSaveState saveState = new FileSaveState();
	private AIMain ai;
	
	public GameState getGameState() { return gameState; }
	
	public FileSaveState getSaveState() { return saveState; }
	
	public AIMain getAI() { return ai; }
	
	public void setAI(AIMain ai) { this.ai = ai; }
}
