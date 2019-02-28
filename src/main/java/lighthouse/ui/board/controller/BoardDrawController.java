package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A responder implementation responsible for drawing new bricks.
 */
public class BoardDrawController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardDrawController.class);
	private final Updatable updater;
	private Board board;
	private IntVec last;
	private boolean dragging = false;
	
	public BoardDrawController(Board model, Updatable updater) {
		this.updater = updater;
		board = model;
	}
	
	@Override
	public void press(IntVec gridPos) {
		if (!board.hasBrickAt(gridPos)) {
			board.getEditState().beginEdit(gridPos);
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			updater.update();
		}
	}
	
	@Override
	public void rightPress(IntVec gridPos) {
		board.removeBrickAt(gridPos);
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			
			if (!gridPos.equals(last)) {
				if (!board.hasBrickAt(gridPos)) {
					board.getEditState().appendToEdit(delta.nearestDirection());
					LOG.debug("Moving {} (delta: {}, last: {}, gridPos: {})", delta.nearestDirection(), delta, last, gridPos);
					updater.update();
				}
				last = gridPos;
			}
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
