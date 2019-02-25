package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.util.IntVec;

/**
 * A responder implementation responsible for editing stuff.
 */
public class BoardEditController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardEditController.class);
	private final Board board;
	private IntVec last;
	
	public BoardEditController(Board model) {
		board = model;
	}
	
	@Override
	public void press(IntVec gridPos) {
		board.getEditState().beginEdit(gridPos);
		LOG.info("Pressed at {}", gridPos);
		last = gridPos;
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		IntVec delta = gridPos.sub(last);
		
		if (!gridPos.equals(last)) {
			board.getEditState().appendToEdit(delta.nearestDirection());
			LOG.info("Moving {} (delta: {}, last: {}, gridPos: {})", delta.nearestDirection(), delta, last, gridPos);
			last = gridPos;
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		LOG.info("Released at {}", gridPos);
		board.add(board.getEditState().finishEdit(gridPos));
		last = null;
	}
}
