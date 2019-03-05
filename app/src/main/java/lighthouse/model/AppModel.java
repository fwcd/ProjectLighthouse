package lighthouse.model;

import lighthouse.ai.AIMain;
import lighthouse.puzzle.model.PuzzleGameState;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	private final PuzzleGameState gameState = new PuzzleGameState();
	private final FileSaveState saveState = new FileSaveState();
	private AIMain ai;
	
	public PuzzleGameState getGameState() { return gameState; }
	
	public FileSaveState getSaveState() { return saveState; }
	
	public AIMain getAI() { return ai; }
	
	public void setAI(AIMain ai) { this.ai = ai; }
}
