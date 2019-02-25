package lighthouse.ui.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.util.MathUtils;

/**
 * A responder implementation responsible for editing stuff.
 */
public class BoardEditController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardEditController.class);
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
		LOG.info("Pressed at {}, {}", gridX, gridY);
	}
	
	@Override
	public void dragTo(int gridX, int gridY) {
		int dx = MathUtils.signum(gridX - lastX);
		int dy = MathUtils.signum(gridY - lastY);
		LOG.info("Moved {}, {}", dx, dy);
		if ((Math.abs(dx) + Math.abs(dy)) == 1) {
			Direction dir = Direction.getDirByOff(dx, dy);
			LOG.info("Added direction {} at {}, {}", dir, gridX, gridY);
			brickInProgress.add(dir);
		}
		if (gridX != lastX || gridY != lastY) {
			lastX = gridX;
			lastY = gridY;
		}
	}
	
	@Override
	public void release(int gridX, int gridY) {
		LOG.info("Released at {}, {}", gridX, gridY);
		board.add(new Brick(startX, startY, brickInProgress));
		brickInProgress = null;
	}
}
