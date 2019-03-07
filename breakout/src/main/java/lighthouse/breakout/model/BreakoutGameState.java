package lighthouse.breakout.model;

import java.util.Iterator;

import lighthouse.model.BaseGameState;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;

public class BreakoutGameState extends BaseGameState {
	private static final double DEFAULT_BRICK_WIDTH = 2;
	private static final double PADDLE_WIDTH = 3;
	private static final double BALL_SPEED = 0.2;
	private static final double BALL_RADIUS = 0.5;
	private final IntVec boardSize = new IntVec(LighthouseConstants.COLS, LighthouseConstants.ROWS);
	
	private final Board board = new Board();
	private final Ball ball = new Ball(boardSize.getX() / 2, boardSize.getY() / 2, BALL_SPEED, BALL_RADIUS);
	private final Paddle paddle = new Paddle((boardSize.getX() / 2) - (PADDLE_WIDTH / 2), boardSize.getY() - 2, PADDLE_WIDTH);
	
	public BreakoutGameState() {
		setupDefaultLevel();
	}
	
	public void advance() {
		if (ball.collidesWith(paddle)) {
			ball.bounceOffHorizontalObstacle();
		}
		
		for (Iterator<Brick> iterator = board.iterator(); iterator.hasNext();) {
			Brick brick = iterator.next();
			if (ball.collidesWith(brick)) {
				ball.bounceOffHorizontalObstacle();
				iterator.remove();
			}
		}
		
		if (ball.isOutOfHorizontalBounds(boardSize.getX())) {
			ball.bounceOffVerticalObstacle();
		} else if (ball.isOutOfVerticalBounds(boardSize.getY())) {
			ball.bounceOffHorizontalObstacle();
		}
		
		ball.move();
	}
	
	private void setupDefaultLevel() {
		for (int y = 2; y < 5; y++) {
			for (int x = 0; x < boardSize.getX(); x++) {
				board.addBrick(new Brick(x, y, DEFAULT_BRICK_WIDTH));
			}
		}
	}
	
	public Board getBoard() { return board; }
	
	public Ball getBall() { return ball; }
	
	public Paddle getPaddle() { return paddle; }
	
	@Override
	public IntVec getGridSize() { return boardSize; }
}
