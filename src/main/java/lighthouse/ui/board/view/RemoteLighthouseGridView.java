package lighthouse.ui.board.view;

import java.awt.Color;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.util.LhConstants;

/**
 * The remote Lighthouse view that uses the API to draw a
 * color grid on the actual highriser.
 */
public class RemoteLighthouseGridView implements LighthouseGridView {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteLighthouseGridView.class);
	private static final int LIGHTHOUSE_BYTES = LhConstants.LIGHTHOUSE_ROWS * LhConstants.LIGHTHOUSE_COLS * 3; // RGB colors
	private final LighthouseDisplay api;

	public RemoteLighthouseGridView(String username, String token) {
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
	public void draw(LighthouseGrid model) {
		try {
			api.send(encode(model));
		} catch (IOException e) {
			LOG.error("An IOException occurred while sending the grid to the Lighthouse: ", e);
		}
	}
	
	/** Encodes the colored grid in a byte array. */
	private byte[] encode(LighthouseGrid grid) {
		if (grid.getRows() != LhConstants.LIGHTHOUSE_ROWS) {
			throw new IllegalArgumentException("Colored grid has " + grid.getRows() + " rows, but should have " + LhConstants.LIGHTHOUSE_ROWS);
		} else if (grid.getColumns() != LhConstants.LIGHTHOUSE_COLS) {
			throw new IllegalArgumentException("Colored grid has " + grid.getColumns() + " columns, but should have " + LhConstants.LIGHTHOUSE_COLS);
		}
		
		byte[] data = new byte[LIGHTHOUSE_BYTES];
		int i = 0;
		
		for (int y = 0; y < LhConstants.LIGHTHOUSE_ROWS; y++) {
			for (int x = 0; x < LhConstants.LIGHTHOUSE_COLS; x++) {
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
