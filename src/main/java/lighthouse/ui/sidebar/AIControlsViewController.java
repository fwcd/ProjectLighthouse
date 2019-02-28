package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;

import lighthouse.model.AppModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIControlsViewController implements ViewController {
	private final JComponent component;
	
	public AIControlsViewController(AppModel appModel) {
		WebSpinner spinner = new WebSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(
				spinner,
				LayoutUtils.buttonOf("Train Population", () -> {})
			)
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
