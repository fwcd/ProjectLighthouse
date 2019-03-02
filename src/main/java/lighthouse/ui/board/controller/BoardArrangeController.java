package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A responder implementation that allows
 * the user to freely arrange bricks.
 */
public class BoardArrangeController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardArrangeController.class);
	private final Updatable updater;
	private BoardViewModel viewModel;
	private IntVec last;
	private boolean dragging = false;
	
	public BoardArrangeController(BoardViewModel viewModel, Updatable updater) {
		this.updater = updater;
		this.viewModel = viewModel;
	}
	
	@Override
	public void press(IntVec gridPos) {
		if (viewModel.hasBrickAt(gridPos)) {
			viewModel.getEditState().beginEdit(viewModel.removeBrickAt(gridPos));
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
			viewModel.getEditState().moveBy(delta);
			last = gridPos;
			updater.update();
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
	
	@Override
	public void select(IntVec gridPos) {
		viewModel.selectAt(gridPos);
		updater.update();
	}
	
	@Override
	public void deselect(IntVec gridPos) {
		viewModel.deselect();
		updater.update();
	}
}
