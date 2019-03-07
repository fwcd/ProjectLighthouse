package lighthouse.breakout.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lighthouse.breakout.model.Ball;
import lighthouse.breakout.model.BreakoutGameState;
import lighthouse.breakout.model.Brick;
import lighthouse.breakout.model.Paddle;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneOval;
import lighthouse.ui.scene.viewmodel.graphics.SceneRect;
import lighthouse.ui.scene.viewmodel.graphics.SceneShape;
import lighthouse.ui.scene.viewmodel.graphics.Shading;

public class BreakoutSceneLayer implements SceneLayer {
	private final BreakoutGameState gameState;
	private final Color brickColor = Color.CYAN;
	private final Color ballColor = Color.YELLOW;
	private final Color paddleColor = Color.ORANGE;

	public BreakoutSceneLayer(BreakoutGameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public List<SceneShape> getShapes() {
		List<SceneShape> shapes = new ArrayList<>();
		
		for (Brick brick : gameState.getBoard()) {
			shapes.add(new SceneRect(brick.getBoundingBox(), brickColor, Shading.FILLED));
		}
		
		Ball ball = gameState.getBall();
		shapes.add(new SceneOval(ball.getPosition(), ball.getRadius(), ballColor, Shading.FILLED));
		
		Paddle paddle = gameState.getPaddle();
		shapes.add(new SceneRect(paddle.getBoundingBox(), paddleColor, Shading.FILLED));
		
		return shapes;
	}
}
