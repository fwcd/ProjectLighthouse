package lighthouse.snake.model;

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
		LOG.debug("Moving up");
		gameState.getSnake().setOrientation(Direction.UP);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec left(IntVec gridPos) {
		LOG.debug("Moving left");
		gameState.getSnake().setOrientation(Direction.LEFT);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec right(IntVec gridPos) {
		LOG.debug("Moving right");
		gameState.getSnake().setOrientation(Direction.RIGHT);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec down(IntVec gridPos) {
		LOG.debug("Moving down");
		gameState.getSnake().setOrientation(Direction.DOWN);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
}
