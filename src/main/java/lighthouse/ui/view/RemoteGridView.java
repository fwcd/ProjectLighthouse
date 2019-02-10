package lighthouse.ui.view;

import java.awt.Color;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Grid;

/**
 * The remote Lighthouse view that uses the API to draw a color grid on the
 * actual highriser.
 */
public class RemoteGridView implements GridView {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteGridView.class);
	private static final int LIGHTHOUSE_WIDTH = 28;
	private static final int LIGHTHOUSE_HEIGHT = 14;
	private static final int LIGHTHOUSE_BYTES = LIGHTHOUSE_WIDTH *LIGHTHOUSE_HEIGHT * 3; // RGB colors
	private final LighthouseDisplay api;

	public RemoteGridView(String username, String token) {
		api = new LighthouseDisplay(username, token);
		// Close the API connection on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(api::close));
	}
	
	/** Connects to the Lighthouse server. */
	public void connect() {
		try {
			api.connect();
		} catch (Exception e) {
			LOG.error("An exception occurred while connecting to the Lighthouse: ", e);
		}
	}
	
	public boolean isConnected() {
		return api.isConnected();
	}

	@Override
	public void draw(Grid model) {
		try {
			api.send(encode(model));
		} catch (IOException e) {
			LOG.error("An IOException occurred while sending the grid to the Lighthouse: ", e);
		}
	}
	
	/** Encodes the colored grid in a byte array. */
	private byte[] encode(Grid grid) {
		if (grid.getHeight() != LIGHTHOUSE_HEIGHT) {
			throw new IllegalArgumentException("Colored grid has " + grid.getHeight() + " rows, but should have " + LIGHTHOUSE_HEIGHT);
		} else if (grid.getWidth() != LIGHTHOUSE_WIDTH) {
			throw new IllegalArgumentException("Colored grid has " + grid.getWidth() + " columns, but should have " + LIGHTHOUSE_WIDTH);
		}
		
		byte[] data = new byte[LIGHTHOUSE_BYTES];
		int i = 0;
		
		for (int y = 0; y < LIGHTHOUSE_HEIGHT; y++) {
			for (int x = 0; x < LIGHTHOUSE_WIDTH; x++) {
				Color cell = grid.getCell(x, y);
				data[i] = (byte) cell.getRed();
				data[i + 1] = (byte) cell.getGreen();
				data[i + 2] = (byte) cell.getBlue();
				i += 3;
			}
		}
		
		return data;
	}
}
