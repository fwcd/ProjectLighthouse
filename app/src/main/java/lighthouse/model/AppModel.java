package lighthouse.model;

import lighthouse.puzzle.model.PuzzleGameState;

/**
 * The application's model. Contains the game.
 */
public class AppModel {
	@Deprecated
	private final PuzzleGameState legacyGameState = new PuzzleGameState();
	private final FileSaveState saveState = new FileSaveState();
	private final GameState activeGameState = null;
	
	@Deprecated
	public PuzzleGameState getGameState() { return legacyGameState; }
	
	public GameState getActiveGameState() { return activeGameState; }
	
	public FileSaveState getSaveState() { return saveState; }
}
