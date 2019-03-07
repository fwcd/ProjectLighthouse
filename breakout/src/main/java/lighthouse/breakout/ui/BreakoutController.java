package lighthouse.breakout.ui;

import lighthouse.breakout.model.BreakoutGameState;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.IntVec;

public class BreakoutController implements SceneResponder {
	private final BreakoutGameState gameState;
	private int paddleSpeed = 2;
	
	public BreakoutController(BreakoutGameState gameState) {
		this.gameState = gameState;
	}
	
	@Override
	public IntVec right(IntVec gridPos) {
		gameState.getPaddle().move(paddleSpeed);
		return gridPos;
	}
	
	@Override
	public IntVec left(IntVec gridPos) {
		gameState.getPaddle().move(-paddleSpeed);
		return gridPos;
	}
}
