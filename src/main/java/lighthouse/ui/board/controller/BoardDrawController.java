package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public void press(IntVec gridPos) {
		BoardViewModel viewModel = getViewModel();
		
		if (!viewModel.hasBrickAt(gridPos)) {
			viewModel.getEditState().beginEdit(gridPos);
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			update();
		}
	}
	
	@Override
	public void rightPress(IntVec gridPos) {
		getViewModel().removeBrickAt(gridPos);
		update();
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			
			if (!gridPos.equals(last)) {
				BoardViewModel viewModel = getViewModel();
				
				if (!viewModel.hasBrickAt(gridPos)) {
					viewModel.getEditState().appendToEdit(delta.nearestDirection());
					LOG.debug("Moving {} (delta: {}, last: {}, gridPos: {})", delta.nearestDirection(), delta, last, gridPos);
					update();
				}
				
				last = gridPos;
			}
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		if (dragging) {
			LOG.debug("Released at {}", gridPos);
			
			BoardViewModel viewModel = getViewModel();
			viewModel.add(viewModel.getEditState().finishEdit(gridPos));
			
			last = null;
			dragging = false;
			update();
		}
	}
}
