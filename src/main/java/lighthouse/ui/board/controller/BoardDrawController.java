package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Brick;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A responder implementation responsible for drawing new bricks.
 */
public class BoardDrawController extends BoardBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BoardDrawController.class);
	private IntVec last;
	private boolean dragging = false;
	
	public BoardDrawController(BoardViewModel viewModel, Updatable updater) {
		super(viewModel, updater);
		setResetEnabled(true);
	}
	
	@Override
	public boolean press(IntVec gridPos) {
		BoardViewModel viewModel = getViewModel();
		
		if (!viewModel.hasBrickAt(gridPos)) {
			viewModel.getEditState().beginEdit(gridPos);
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			update();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean rightPress(IntVec gridPos) {
		Brick removed = getViewModel().removeBrickAt(gridPos);
		if (removed != null) {
			update();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean dragTo(IntVec gridPos) {
		boolean updated = false;
		
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			
			if (!gridPos.equals(last)) {
				BoardViewModel viewModel = getViewModel();
				
				if (!viewModel.hasBrickAt(gridPos)) {
					viewModel.getEditState().appendToEdit(delta.nearestDirection());
					LOG.debug("Moving {} (delta: {}, last: {}, gridPos: {})", delta.nearestDirection(), delta, last, gridPos);
					update();
					updated = true;
				}
				
				last = gridPos;
			}
		}
		
		return updated;
	}
	
	@Override
	public boolean release(IntVec gridPos) {
		if (dragging) {
			LOG.debug("Released at {}", gridPos);
			
			BoardViewModel viewModel = getViewModel();
			viewModel.add(viewModel.getEditState().finishEdit(gridPos));
			
			last = null;
			dragging = false;
			update();
			return true;
		} else {
			return false;
		}
	}
}
