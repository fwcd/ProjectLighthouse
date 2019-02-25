package lighthouse.ui.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.util.IntVec;

/**
 * A responder implementation responsible for editing stuff.
 */
public class BoardEditController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardEditController.class);
	private final Board board;
	private List<Direction> brickInProgress;
	private IntVec last;
	private IntVec start;
	
	public BoardEditController(Board model) {
		board = model;
	}
	
	@Override
	public void press(IntVec gridPos) {
		start = gridPos;
		last = gridPos;
		brickInProgress = new ArrayList<>();
		LOG.info("Pressed at {}", gridPos);
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		IntVec delta = gridPos.sub(last);
		
		if (!gridPos.equals(last)) {
			brickInProgress.add(delta.nearestDirection());
			LOG.info("Moving {} (delta: {}, last: {}, gridPos: {})", delta.nearestDirection(), delta, last, gridPos);
			last = gridPos;
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		LOG.info("Released at {}", gridPos);
		board.add(new Brick(start, brickInProgress));
		
		start = null;
		last = null;
		brickInProgress = null;
	}
}
