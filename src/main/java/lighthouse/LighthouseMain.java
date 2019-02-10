package lighthouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lighthouse.model.AppModel;

/**
 * The application's entry point. Instantiates
 * fundamental parts of the program, such as the
 * model or the window (called "frame").
 */
public class LighthouseMain extends Application {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseMain.class);
	private final AppModel model = new AppModel();
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		launch(args);
		LOG.info("Launched application in {} ms", System.currentTimeMillis() - startTime);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Pane());
		
		stage.setScene(scene);
		stage.show();
	}
}
