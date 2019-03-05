package lighthouse.puzzle;

import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.ui.ViewController;

public class PuzzleGame implements Game {
	private final PuzzleGameState model = new PuzzleGameState();
	private final ViewController centralViewController;
	private final ViewController controlsViewController;
	
	public PuzzleGame() {
		throw new RuntimeException("TODO");
	}
	
	@Override
	public GameState getModel() { return model; }
	
	@Override
	public ViewController getCentralViewController() { return centralViewController; }
	
	@Override
	public ViewController getControlsViewController() { return controlsViewController; }
}
