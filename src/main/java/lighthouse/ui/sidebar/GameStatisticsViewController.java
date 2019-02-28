package lighthouse.ui.sidebar;

import javax.swing.JComponent;

import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class GameStatisticsViewController implements ViewController {
	private final JComponent component;
	
	public GameStatisticsViewController() {
		component = LayoutUtils.vboxOf(
			
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
