package lighthouse.puzzle;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameInitializationContext;
import lighthouse.model.GameState;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.PuzzleGameManager;
import lighthouse.puzzle.ui.sidebar.BoardStatisticsViewController;
import lighthouse.puzzle.ui.sidebar.GameControlsViewController;
import lighthouse.puzzle.ui.sidebar.SolverViewController;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;
import lighthouse.util.transform.Translation;

/**
 * The original puzzle game.
 */
public class PuzzleGame implements Game {
	private static final Logger LOG = LoggerFactory.getLogger(PuzzleGame.class);
	private final PuzzleGameState model = new PuzzleGameState();
	private PuzzleGameManager game;
	private SwingViewController controls;
	private SwingViewController solver;
	private SwingViewController statistics;
	
	private final DoubleVecBijection gridPosToPixels = new Scaling(70, 70);
	private final DoubleVecBijection lighthouseToGridSize = new Scaling(0.2, 0.5);
	private final DoubleVecBijection lighthouseToGridPos = new Translation(-4, -1).andThen(lighthouseToGridSize);
	
	@Override
	public void initialize(GameInitializationContext context) {
		loadDefaultLevel();
		game = new PuzzleGameManager(model, context);
		controls = new GameControlsViewController(game, model);
		solver = new SolverViewController(model, context.getAnimationRunner());
		statistics = new BoardStatisticsViewController(game.getBoardViewModel().getStatistics());
	}
	
	private void loadDefaultLevel() {
		try (InputStream stream = PuzzleGame.class.getResourceAsStream("/levels/default.json")) {
			// Load default level
			model.loadLevelFrom(stream);
		} catch (Exception e) {
			LOG.warn("Exception while loading default level:", e);
		}
	}
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridPosToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return lighthouseToGridPos; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridSize() { return lighthouseToGridSize; }
	
	@Override
	public SceneLayer getGameLayer() { return game.getBoardViewModel(); }
	
	@Override
	public String getName() { return "Puzzle"; }
	
	@Override
	public GameState getModel() { return model; }
	
	@Override
	public boolean hasCustomGameViewController() { return true; }
	
	@Override
	public SwingViewController getControlsViewController() { return controls; }
	
	@Override
	public SwingViewController getSolverViewController() { return solver; }
	
	@Override
	public SwingViewController getStatisticsViewController() { return statistics; }
}
