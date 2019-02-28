package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A responder implementation that allows
 * the user to freely arrange bricks.
 */
public class BoardArrangeController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardArrangeController.class);
	private final Updatable updater;
	private Board board;
	private IntVec last;
	private boolean dragging = false;
	
	public BoardArrangeController(Board model, Updatable updater) {
		this.updater = updater;
		board = model;
	}
	
	@Override
	public void press(IntVec gridPos) {
		if (board.hasBrickAt(gridPos)) {
			board.getEditState().beginEdit(board.removeBrickAt(gridPos));
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			updater.update();
		}
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			board.getEditState().moveBy(delta);
			last = gridPos;
			updater.update();
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		if (dragging) {
			LOG.debug("Released at {}", gridPos);
			board.add(board.getEditState().finishEdit(gridPos));
			last = null;
			dragging = false;
			updater.update();
		}
	}
	
	@Override
	public void reset() {
		LOG.debug("Resetting");
		board.clear();
		updater.update();
	}
	
	@Override
	public void updateBoard(Board board) {
		this.board = board;
	}
}
