package lighthouse.snake;

import javax.swing.Timer;

import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameInitializationContext;
import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.model.GameState;
import lighthouse.snake.ui.SnakeController;
import lighthouse.snake.ui.SnakeGameState;
import lighthouse.snake.ui.SnakeSceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

/**
 * A snake game.
 */
public class SnakeGame implements Game {
	// private static final Logger LOG = LoggerFactory.getLogger(SnakeGame.class);
	private final SnakeGameState gameState = new SnakeGameState();
	private final SnakeSceneLayer sceneLayer = new SnakeSceneLayer(gameState);
	private final DoubleVecBijection gridPosToPixels = new Scaling(15, 15);
	private final Timer timer;
	private final int maxTPS = 4;
	private SceneInteractionFacade sceneFacade;
	
	public SnakeGame() {
		timer = new Timer(1000 / maxTPS, e -> {
			gameState.advance();
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
	public boolean usesSimpleArrowKeys() { return true; }
	
	@Override
	public GameState getModel() { return gameState; }
	
	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridPosToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return DoubleVecBijection.IDENTITY; }
}
