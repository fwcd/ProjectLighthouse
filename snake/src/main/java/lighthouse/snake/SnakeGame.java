package lighthouse.snake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.Timer;
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
	private final Timer timer;
	private final int maxFPS = 2;
	private SceneInteractionFacade sceneFacade;
	
	public SnakeGame() {
		timer = new Timer(1000 / maxFPS, e -> {
			gameState.move();
			sceneFacade.update();
		});
		timer.setRepeats(true);
	}
	
	@Override
	public String getName() { return "Snake"; }
	
	@Override
	public void initialize(GameInitializationContext context) {
		sceneFacade = context.getInteractionFacade();
	}
	
	@Override
	public void onOpen() {
		sceneFacade.setResponder(new SnakeController(gameState, sceneFacade));
		timer.start();
	}
	
	@Override
	public void onClose() {
		timer.stop();
	}
	
	@Override
	public boolean hasSimpleArrowKeys() { return true; }
	
	@Override
	public GameState getModel() { return gameState; }
	
	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridPosToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return DoubleVecBijection.IDENTITY; }
}
