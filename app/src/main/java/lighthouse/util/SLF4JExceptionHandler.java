package lighthouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final Logger LOG = LoggerFactory.getLogger(SLF4JExceptionHandler.class);
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOG.error("An error ocurred on the EDT:", e);
	}
}
