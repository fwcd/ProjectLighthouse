package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;
import lighthouse.puzzle.ui.board.controller.BoardDrawController;
import lighthouse.puzzle.ui.board.controller.BoardResponder;
import lighthouse.puzzle.ui.board.input.BoardMouseInput;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.ViewController;
import lighthouse.ui.scene.view.LocalBoardView;
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
	private Board board;
	private BoardViewModel boardViewModel;
	
	public BlockedStatesEditorViewController(Level level) {
		this.level = level;
		
		component = new JPanel();
		component.setBorder(new EmptyBorder(padding, padding, padding, padding));
		component.setLayout(new BorderLayout());
		
		board = new Board();
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
			LayoutUtils.buttonOf("Add", () -> add(board)),
			LayoutUtils.buttonOf("Remove All", () -> removeAll())
		), BorderLayout.EAST);
	}
	
	private void add(Board board) {
		if (!board.isEmpty()) {
			level.getBlockedStates().add(board.copy());
			clearBoard();
			updateCountLabel();
		}
	}
	
	private void removeAll() {
		level.getBlockedStates().clear();
		clearBoard();
		updateCountLabel();
	}
	
	private void clearBoard() {
		board = new Board();
		boardViewModel = new BoardViewModel(board);
		view.draw(boardViewModel);
		controller.updateViewModel(boardViewModel);
	}
	
	private void updateCountLabel() {
		countLabel.setText(level.getBlockedStates().size() + " blocked states");
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
