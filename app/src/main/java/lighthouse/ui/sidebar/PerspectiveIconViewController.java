package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.scene.view.LocalBoardView;
import lighthouse.ui.util.CenterPanel;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class PerspectiveIconViewController implements ViewController {
	private final JComponent component;
	
	public PerspectiveIconViewController(GamePerspective perspective, GameViewController game) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		DoubleVecBijection gridToPixels = new Scaling(4, 4);
		LocalBoardView boardView = new LocalBoardView(gridToPixels);
		boardView.setActiveBrickScale(1.0);
		boardView.setPlacedBrickScale(1.0);
		boardView.setDrawGrid(false);
		boardView.setEdgeDrawMode(LocalBoardView.EdgeDrawMode.NONE);
		
		Board initialBoard = perspective.getActiveBoard(game.getModel());
		boardView.relayout(initialBoard.getColumns(), initialBoard.getRows());
		
		game.getExternalUpdaters().add(() -> boardView.draw(new BoardViewModel(perspective.getActiveBoard(game.getModel()))));
		component.add(new CenterPanel(boardView.getComponent()), BorderLayout.CENTER);
		component.add(new JLabel(perspective.getName()), BorderLayout.SOUTH);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
