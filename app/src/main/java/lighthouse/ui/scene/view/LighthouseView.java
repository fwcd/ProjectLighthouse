package lighthouse.ui.scene.view;

import lighthouse.ui.scene.viewmodel.LighthouseViewModel;

/**
 * The presentation component of the Lighthouse grid.
 */
public interface LighthouseView {
	void draw(LighthouseViewModel viewModel);
}
