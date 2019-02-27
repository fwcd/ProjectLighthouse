package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class AIControlsViewController implements ViewController {
	private final JComponent component;
	
	public AIControlsViewController() {
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Train", () -> {})
			)
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
