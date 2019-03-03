package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lighthouse.model.Board;
import lighthouse.model.Level;
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
	private final int padding = 15;
	private final JPanel component;
	private final JLabel countLabel;
	private final Level level;
	
	private final LocalBoardView view;
	private final BoardResponder controller;
	private BoardViewModel boardViewModel;
	
	public BlockedStatesEditorViewController(Level level) {
		this.level = level;
		
		component = new JPanel();
		component.setBorder(new EmptyBorder(padding, padding, padding, padding));
		component.setLayout(new BorderLayout());
		
		Board board = new Board();
		DoubleVecBijection gridToPixels = new Scaling(30, 30);
		boardViewModel = new BoardViewModel(board);
		view = new LocalBoardView(gridToPixels);
		controller = new BoardDrawController(boardViewModel, () -> view.draw(boardViewModel));
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
			LayoutUtils.buttonOf("Add", () -> add(board.copy())),
			LayoutUtils.buttonOf("Remove All", () -> removeAll())
		), BorderLayout.EAST);
	}
	
	private void add(Board board) {
		level.getBlockedStates().add(board);
		clearBoard();
		updateCountLabel();
	}
	
	private void removeAll() {
		level.getBlockedStates().clear();
		clearBoard();
		updateCountLabel();
	}
	
	private void clearBoard() {
		boardViewModel = new BoardViewModel(new Board());
		view.draw(boardViewModel);
		controller.updateViewModel(boardViewModel);
	}
	
	private void updateCountLabel() {
		countLabel.setText(level.getBlockedStates().size() + " blocked states");
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
