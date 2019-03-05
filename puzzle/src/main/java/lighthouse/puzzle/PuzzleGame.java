package lighthouse.puzzle;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.GameViewController;
import lighthouse.puzzle.ui.sidebar.BoardStatisticsViewController;
import lighthouse.puzzle.ui.sidebar.GameControlsViewController;
import lighthouse.puzzle.ui.sidebar.SolverViewController;
import lighthouse.ui.AppContext;
import lighthouse.ui.ViewController;

/**
 * The original puzzle game.
 */
public class PuzzleGame implements Game {
	private static final Logger LOG = LoggerFactory.getLogger(PuzzleGame.class);
	private final PuzzleGameState model = new PuzzleGameState();
	private GameViewController game;
	private ViewController controls;
	private ViewController solver;
	private ViewController statistics;
	
	@Override
	public void initialize(AppContext context) {
		loadDefaultLevel();
		game = new GameViewController(model, context);
		controls = new GameControlsViewController(game, model);
		solver = new SolverViewController(model, game.getBoard());
		statistics = new BoardStatisticsViewController(game.getBoard().getViewModel().getStatistics());
	}
	
	private void loadDefaultLevel() {
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
	public ViewController getGameViewController() { return game; }
	
	@Override
	public ViewController getControlsViewController() { return controls; }
	
	@Override
	public ViewController getSolverViewController() { return solver; }
	
	@Override
	public ViewController getStatisticsViewController() { return statistics; }
}
