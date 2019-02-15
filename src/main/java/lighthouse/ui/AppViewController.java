package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.AppModel;
import lighthouse.ui.grid.GridViewController;

/**
 * The application's base view controller.
 */
public class AppViewController {
	private final JComponent component;
	
	public AppViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		GridViewController board = new GridViewController(model.getGrid());
		component.add(board.getComponent(), BorderLayout.CENTER);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
