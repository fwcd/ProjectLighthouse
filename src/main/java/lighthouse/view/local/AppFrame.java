package lighthouse.view.local;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import lighthouse.model.AppModel;

/**
 * The application window.
 */
public class AppFrame {
	private final JFrame frame;
	
	public AppFrame(AppModel model) {
		frame = new JFrame("Lighthouse");
		frame.setSize(640, 480);
		frame.setLayout(new BorderLayout());
		frame.add(new AppView(model).getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
