package lighthouse.view.local;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.AppModel;

public class AppView {
	private final JComponent component;
	
	public AppView(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		component.add(new GameBoardView(model.getBoard()).getComponent(), BorderLayout.CENTER);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
