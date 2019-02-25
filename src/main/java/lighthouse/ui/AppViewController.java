package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.AppModel;
import lighthouse.ui.sidebar.SideBarViewController;

/**
 * The application's base view controller.
 */
public class AppViewController {
	private final JComponent component;
	
	public AppViewController(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		SideBarViewController sideBar = new SideBarViewController(model);
		component.add(sideBar.getComponent(), BorderLayout.EAST);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
