package lighthouse.puzzle;

import javax.swing.JLabel;

import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.ui.ViewController;

public class PuzzleGame implements Game {
	private final PuzzleGameState model = new PuzzleGameState();
	private final ViewController centralViewController;
	private final ViewController controlsViewController;
	
	public PuzzleGame() {
		// TODO
		centralViewController = () -> new JLabel("Central view");
		controlsViewController = () -> new JLabel("Controls view");
	}
	
	@Override
	public String getName() { return "Puzzle"; }
	
	@Override
	public GameState getModel() { return model; }
	
	@Override
	public ViewController getGameViewController() { return centralViewController; }
	
	@Override
	public ViewController getControlsViewController() { return controlsViewController; }
}
