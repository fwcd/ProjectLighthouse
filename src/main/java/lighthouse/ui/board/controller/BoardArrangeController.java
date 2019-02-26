package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.util.IntVec;

/**
 * A responder implementation that allows
 * the user to freely arrange bricks.
 */
public class BoardArrangeController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardArrangeController.class);
	private Board board;
	private IntVec last;
	private boolean dragging = false;
	
	public BoardArrangeController(Board model) {
		board = model;
	}
	
	@Override
	public void press(IntVec gridPos) {
		if (board.hasBrickAt(gridPos)) {
			board.getEditState().beginEdit(board.removeBrickAt(gridPos));
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
		}
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			board.getEditState().getBrickInProgress().moveBy(delta);
			last = gridPos;
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		if (dragging) {
			LOG.debug("Released at {}", gridPos);
			board.add(board.getEditState().finishEdit(gridPos));
			last = null;
			dragging = false;
		}
	}
	
	@Override
	public void reset() {
		LOG.debug("Resetting");
		board.clear();
	}
	
	@Override
	public void updateBoard(Board board) {
		this.board = board;
	}
}
