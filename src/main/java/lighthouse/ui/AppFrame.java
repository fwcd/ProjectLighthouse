package lighthouse.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.stage.Stage;
import lighthouse.model.AppModel;

/**
 * The application window.
 */
public class AppFrame extends Application {
	private final JFrame frame;
	
	public AppFrame(AppModel model) {
		frame = new JFrame("Lighthouse");
		frame.setSize(640, 480);
		frame.setLayout(new BorderLayout());
		frame.add(new AppViewController(model).getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
