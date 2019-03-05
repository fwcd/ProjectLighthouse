package lighthouse.ui;

import java.awt.BorderLayout;
import java.util.Optional;

import javax.swing.JFrame;

import lighthouse.model.AppModel;

/**
 * The application window.
 */
public class AppFrame {
	private final JFrame frame;
	
	public AppFrame(AppModel model, String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.add(new AppViewController(model).getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Automatically update the window's title based
		// on the save destination.
		model.getSaveState().getSaveDestinationListeners().add(path -> {
			String newTitle = title + Optional.ofNullable(path)
				.map(it -> " - " + it.getFileName().toString())
				.orElse("");
			frame.setTitle(newTitle);
		});
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
