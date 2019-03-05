package lighthouse.puzzle;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.ui.AppContext;
import lighthouse.ui.ViewController;

public class PuzzleGame implements Game {
	private static final Logger LOG = LoggerFactory.getLogger(PuzzleGame.class);
	private final PuzzleGameState model = new PuzzleGameState();
	private final ViewController centralViewController;
	private final ViewController controlsViewController;
	
	public PuzzleGame() {
		// TODO
		centralViewController = () -> new JLabel("Central view");
		controlsViewController = () -> new JLabel("Controls view");
	}
	
	@Override
	public void initialize(AppContext context) {
		try (InputStream stream = PuzzleGame.class.getResourceAsStream("/levels/default.json")) {
			// Load default level
			model.loadLevelFrom(stream);
		} catch (IOException e) {
			LOG.warn("Exception while loading default level:", e);
		}
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
