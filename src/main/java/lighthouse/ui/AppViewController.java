package lighthouse.ui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.AppModel;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.loop.GameLoop;
import lighthouse.ui.sidebar.SideBarViewController;

/**
 * The application's base view controller.
 */
public class AppViewController implements ViewController {
	private final JComponent component;
	private final GameLoop loop = new GameLoop();
	
	public AppViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JPanel boardWrapper = new JPanel();
		boardWrapper.setLayout(new GridBagLayout());
		
		BoardViewController board = new BoardViewController(model.getGame().getBoard());
		boardWrapper.add(board.getComponent());
		model.getGame().getBoardListeners().add(board::updateModel);
		component.add(boardWrapper, BorderLayout.CENTER);
		
		SideBarViewController sideBar = new SideBarViewController(model, board, loop);
		component.add(sideBar.getComponent(), BorderLayout.EAST);
		
		// Start the game loop
		loop.addRenderer(board);
		loop.start();
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
