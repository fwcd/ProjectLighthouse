package lighthouse.ui.sidebar;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lighthouse.model.Game;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.ScaleTransform;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.ui.loop.GameLoop;
import lighthouse.ui.stage.GameStage;
import lighthouse.ui.util.CenterPanel;

public class GameStageIconViewController implements ViewController {
	private final JComponent component;
	
	public GameStageIconViewController(GameStage stage, Game game, GameLoop loop) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		stage.getBoardFrom(game.getState())
			.ifPresent(initialBoard -> {
				LocalBoardView boardView = new LocalBoardView(new ScaleTransform(4, 4));
				
				boardView.setActiveBrickScale(1.0);
				boardView.setPlacedBrickScale(1.0);
				boardView.setDrawGrid(false);
				boardView.relayout(initialBoard.getColumns(), initialBoard.getRows());
				
				loop.addRenderer(() -> stage.getBoardFrom(game.getState()).ifPresent(boardView::draw));
				component.add(new CenterPanel(boardView.getComponent()), BorderLayout.CENTER);
			});
		component.add(new JLabel(stage.getName()), BorderLayout.SOUTH);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
