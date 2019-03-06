package lighthouse.snake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameInitializationContext;
import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.model.GameState;
import lighthouse.snake.model.SnakeSceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;
import lighthouse.snake.model.SnakeController;
import lighthouse.snake.model.SnakeGameState;

/**
 * A snake game.
 */
public class SnakeGame implements Game {
	// private static final Logger LOG = LoggerFactory.getLogger(SnakeGame.class);
	private final SnakeGameState gameState = new SnakeGameState();
	private final SnakeSceneLayer sceneLayer = new SnakeSceneLayer(gameState);
	private final DoubleVecBijection gridPosToPixels = new Scaling(10, 20);
	private SceneInteractionFacade sceneFacade;
	
	@Override
	public String getName() { return "Snake"; }
	
	@Override
	public void initialize(GameInitializationContext context) {
		sceneFacade = context.getInteractionFacade();
	}
	
	@Override
	public void onOpen() {
		sceneFacade.setResponder(new SnakeController(gameState, sceneFacade));
	}
	
	@Override
	public GameState getModel() { return gameState; }
	
	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridPosToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return DoubleVecBijection.IDENTITY; }
}
