package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JLabel;

import lighthouse.model.GameStatistics;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;

public class GameStatisticsViewController implements ViewController {
	private final JComponent component;
	
	public GameStatisticsViewController(GameStatistics model) {
		component = LayoutUtils.vboxOf(
			LayoutUtils.compoundOf(
				new JLabel("Move count:"),
				LayoutUtils.labelOf(model.getMoveCount(), model.getMoveCountListeners())
			),
			LayoutUtils.compoundOf(
				new JLabel("Avg. distance to goal:"),
				LayoutUtils.labelOf(model.getAvgDistanceToGoal(), model.getDistanceToGoalListeners())
			)
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
