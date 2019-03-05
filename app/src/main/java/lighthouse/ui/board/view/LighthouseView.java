package lighthouse.ui.board.view;

import lighthouse.ui.board.viewmodel.LighthouseViewModel;

/**
 * The presentation component of the Lighthouse grid.
 */
public interface LighthouseView {
	void draw(LighthouseViewModel viewModel);
}
