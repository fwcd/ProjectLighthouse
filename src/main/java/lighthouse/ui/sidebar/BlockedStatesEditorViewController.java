package lighthouse.ui.sidebar;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lighthouse.model.Board;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.controller.BoardDrawController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class BlockedStatesEditorViewController implements ViewController {
	private final JPanel component;
	private final JLabel countLabel;
	private final int padding = 15;
	private List<Board> blockedStates = new ArrayList<>();
	
	public BlockedStatesEditorViewController() {
		component = new JPanel();
		component.setBorder(new EmptyBorder(padding, padding, padding, padding));
		component.setLayout(new BorderLayout());
		
		Board board = new Board();
		BoardViewModel boardViewModel = new BoardViewModel(board);
		DoubleVecBijection gridToPixels = new Scaling(20, 20);
		LocalBoardView view = new LocalBoardView(gridToPixels);
		BoardResponder controller = new BoardDrawController(boardViewModel, () -> view.draw(boardViewModel));
		BoardMouseInput input = new BoardMouseInput(gridToPixels);
		
		view.relayout(board.getColumns(), board.getRows());
		view.draw(boardViewModel);
		input.addResponder(controller);
		view.addMouseInput(input);
		
		component.add(view.getComponent(), BorderLayout.CENTER);
		
		countLabel = new JLabel("?");
		updateCountLabel();
		
		component.add(LayoutUtils.vboxOf(
			countLabel,
			LayoutUtils.buttonOf("Add", () -> {}),
			LayoutUtils.buttonOf("Remove All", () -> {})
		), BorderLayout.EAST);
	}
	
	private void updateCountLabel() {
		countLabel.setText(blockedStates.size() + " blocked states");
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
