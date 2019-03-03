package lighthouse.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.Updatable;

/**
 * A base implementation of {@link BoardResponder}
 * that provides common functionality for controllers.
 */
public abstract class BoardBaseController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardBaseController.class);
	private final Updatable updater;
	private BoardViewModel viewModel;
	private boolean resetEnabled = false;
	
	public BoardBaseController(BoardViewModel viewModel, Updatable updater) {
		this.viewModel = viewModel;
		this.updater = updater;
	}
	
	protected boolean isResetEnabled() { return resetEnabled; }
	
	protected void setResetEnabled(boolean resetEnabled) { this.resetEnabled = resetEnabled; }
	
	protected BoardViewModel getViewModel() { return viewModel; }
	
	protected void update() {
		updater.update();
	}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	@Override
	public void reset() {
		if (resetEnabled) {
			LOG.debug("Resetting");
			getViewModel().clear();
			update();
		}
	}
	
	// TODO: Selection handling
}
