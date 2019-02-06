package lighthouse.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The application interface. Passively renders.
 */
public class AppView {
	private final JComponent component;
	private final BoardView board;
	
	public AppView(BoardView board) {
		this.board = board;
		component = new JPanel();
		component.setLayout(new BorderLayout());
		component.add(board.getComponent(), BorderLayout.CENTER);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
