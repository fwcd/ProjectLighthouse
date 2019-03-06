package lighthouse.snake.model;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.Direction;
import lighthouse.util.IntVec;

public class SnakeController implements SceneResponder {
	private final SnakeGameState gameState;
	private final SceneInteractionFacade sceneFacade;
	
	public SnakeController(SnakeGameState gameState, SceneInteractionFacade sceneFacade) {
		this.gameState = gameState;
		this.sceneFacade = sceneFacade;
	}
	
	@Override
	public IntVec up(IntVec gridPos) {
		gameState.getSnake().setOrientation(Direction.UP);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec left(IntVec gridPos) {
		gameState.getSnake().setOrientation(Direction.LEFT);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
	
	@Override
	public IntVec right(IntVec gridPos) {
		gameState.getSnake().setOrientation(Direction.RIGHT);
		gameState.move();
		sceneFacade.update();
		return gridPos;
	}
}
