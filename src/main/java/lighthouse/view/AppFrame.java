package lighthouse.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The application window.
 */
public class AppFrame {
	private final JFrame frame;
	private final AppController viewController = new AppController();
	
	public AppFrame() {
		frame = new JFrame("Lighthouse");
		frame.setSize(640, 480);
		frame.setLayout(new BorderLayout());
		frame.add(viewController.getView().getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
