package lighthouse.ui.board.controller;

import java.util.ArrayList;
import java.util.List;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.util.MathUtils;

/**
 * A responder implementation responsible for editing stuff.
 */
public class BoardEditController implements BoardResponder {
	private final Board board;
	private List<Direction> brickInProgress;
	private int lastX;
	private int lastY;
	private int startX;
	private int startY;
	
	public BoardEditController(Board model) {
		board = model;
	}
	
	@Override
	public void press(int gridX, int gridY) {
		startX = gridX;
		startY = gridY;
		brickInProgress = new ArrayList<>();
	}
	
	@Override
	public void dragTo(int gridX, int gridY) {
		int dx = MathUtils.signum(gridX - lastX);
		int dy = MathUtils.signum(gridY - lastY);
		brickInProgress.add(Direction.getDirByOff(dx, dy));
	}
	
	@Override
	public void release(int gridX, int gridY) {
		board.add(new Brick(startX, startY, brickInProgress));
		brickInProgress = null;
	}
}
