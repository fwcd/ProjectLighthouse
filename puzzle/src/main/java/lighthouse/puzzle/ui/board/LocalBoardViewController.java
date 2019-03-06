package lighthouse.puzzle.ui.board;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.SwingViewController;

public class LocalBoardViewController implements SwingViewController {
	private final JPanel component;
	private final BoardViewModel viewModel;
	
	public LocalBoardViewController(Board model) {
		viewModel = new BoardViewModel(model);
		component = new JPanel();
	}
	
	public BoardViewModel getViewModel() { return viewModel; }
	
	@Override
	public JComponent getComponent() { return component; }
}
