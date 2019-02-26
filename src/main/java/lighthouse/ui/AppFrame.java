package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import lighthouse.model.AppModel;

/**
 * The application window.
 */
public class AppFrame {
	private final JFrame frame;
	
	public AppFrame(AppModel model, int width, int height) {
		frame = new JFrame("Lighthouse");
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.add(new AppViewController(model).getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
