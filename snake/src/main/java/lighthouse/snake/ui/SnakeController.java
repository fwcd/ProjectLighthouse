package lighthouse.snake.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.Direction;
import lighthouse.util.IntVec;

public class SnakeController implements SceneResponder {
	private static final Logger LOG = LoggerFactory.getLogger(SnakeController.class);
	private final SnakeGameState gameState;
	private final SceneInteractionFacade sceneFacade;
	
	public SnakeController(SnakeGameState gameState, SceneInteractionFacade sceneFacade) {
		this.gameState = gameState;
		this.sceneFacade = sceneFacade;
	}
	
	@Override
	public IntVec up(IntVec gridPos) {
		LOG.debug("Moving snake up");
		gameState.getSnake().setOrientation(Direction.UP);
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec left(IntVec gridPos) {
		LOG.debug("Moving snake left");
		gameState.getSnake().setOrientation(Direction.LEFT);
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec right(IntVec gridPos) {
		LOG.debug("Moving snake right");
		gameState.getSnake().setOrientation(Direction.RIGHT);
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec down(IntVec gridPos) {
		LOG.debug("Moving snake down");
		gameState.getSnake().setOrientation(Direction.DOWN);
		sceneFacade.update();
		return gridPos;
	}
}
