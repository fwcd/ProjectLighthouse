package lighthouse;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.ui.AppFrame;
import lighthouse.util.SLF4JExceptionHandler;
import uk.org.lidalia.sysoutslf4j.context.LogLevel;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * The application's entry point. Instantiates
 * fundamental parts of the program, such as the
 * model or the window (called "frame").
 */
public class LighthouseMain {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseMain.class);
	private static final String TITLE = "Lighthouse";
	private static final int WINDOW_WIDTH = 680;
	private static final int WINDOW_HEIGHT = 550;
	
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		long startTime = System.currentTimeMillis();
		
		// Redirect System.out and .err to the logging framework
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.INFO, LogLevel.WARN);
		// Register handler for uncaught exceptions on the AWT event thread
		SwingUtilities.invokeLater(() -> {
			Thread.currentThread().setUncaughtExceptionHandler(new SLF4JExceptionHandler());
		});
		
		AppModel model = new AppModel();
		SwingUtilities.invokeAndWait(() -> {
			WebLookAndFeel.install();
			AppFrame frame = new AppFrame(model, TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
			frame.show();
		});
		
		LOG.info("Launched application in {} ms", System.currentTimeMillis() - startTime);
	}
}
