package lighthouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.ui.AppFrame;

/**
 * The application's entry point. Instantiates
 * fundamental parts of the program, such as the
 * model or the window (called "frame").
 */
public class LighthouseMain {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseMain.class);
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		AppModel model = new AppModel();
		AppFrame frame = new AppFrame(model);
		frame.show();
		
		LOG.info("Launched application in {} ms", System.currentTimeMillis() - startTime);
	}
}
