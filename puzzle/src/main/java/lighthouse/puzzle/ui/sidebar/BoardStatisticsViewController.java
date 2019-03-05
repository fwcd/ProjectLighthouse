package lighthouse.puzzle.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JLabel;

import lighthouse.ui.SwingViewController;
import lighthouse.puzzle.ui.board.viewmodel.BoardStatistics;
import lighthouse.ui.util.LayoutUtils;

public class BoardStatisticsViewController implements SwingViewController {
	private final JComponent component;
	
	public BoardStatisticsViewController(BoardStatistics model) {
		component = LayoutUtils.vboxOf(
			LayoutUtils.compoundOf(
				new JLabel("Move count:"),
				LayoutUtils.labelOf(model.getMoveCount(), model.getMoveCountListeners())
			),
			LayoutUtils.compoundOf(
				new JLabel("Avg. distance to goal:"),
				LayoutUtils.labelOf(model.getAvgDistanceToGoal(), model.getDistanceToGoalListeners(), it -> String.format("%.2f", it))
			)
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
