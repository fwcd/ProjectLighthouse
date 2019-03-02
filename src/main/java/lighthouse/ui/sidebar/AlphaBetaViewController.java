package lighthouse.ui.sidebar;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.alphabeta.AlphaBeta;
import lighthouse.model.Board;
import lighthouse.model.GameState;
import lighthouse.model.Level;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.util.LayoutUtils;

public class AlphaBetaViewController implements ViewController {
	private final JPanel component;
	private final BoardViewController board;
	private final GameState gameState;
	
	public AlphaBetaViewController(GameState gameState, BoardViewController board) {
		this.gameState = gameState;
		this.board = board;
		component = LayoutUtils.vboxOf(
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("AlphaBeta search", this::alphaBetaSearch)
			)
		);
	}
	
	private void alphaBetaSearch() {
		new Thread(() -> {
			Level level = gameState.getLevel();
			List<Board> solution = AlphaBeta.solve(level);
			board.play(solution, 100);
		}, "AlphaBeta").start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
