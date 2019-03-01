package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A responder implementation responsible for drawing new bricks.
 */
public class BoardDrawController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardDrawController.class);
	private final Updatable updater;
	private BoardViewModel viewModel;
	private IntVec last;
	private boolean dragging = false;
	
	public BoardDrawController(BoardViewModel viewModel, Updatable updater) {
		this.updater = updater;
		this.viewModel = viewModel;
	}
	
	@Override
	public void press(IntVec gridPos) {
		if (!viewModel.hasBrickAt(gridPos)) {
			viewModel.getEditState().beginEdit(gridPos);
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			updater.update();
		}
	}
	
	@Override
	public void rightPress(IntVec gridPos) {
		viewModel.removeBrickAt(gridPos);
		updater.update();
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			
			if (!gridPos.equals(last)) {
				if (!viewModel.hasBrickAt(gridPos)) {
					viewModel.getEditState().appendToEdit(delta.nearestDirection());
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
			viewModel.add(viewModel.getEditState().finishEdit(gridPos));
			last = null;
			dragging = false;
			updater.update();
		}
	}
	
	@Override
	public void reset() {
		LOG.debug("Resetting");
		viewModel.clear();
		updater.update();
	}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {
		this.viewModel = viewModel;
	}
}
