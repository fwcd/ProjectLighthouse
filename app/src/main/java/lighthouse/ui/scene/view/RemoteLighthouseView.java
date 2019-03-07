package lighthouse.ui.scene.view;

import java.awt.Color;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.scene.input.SceneLighthouseInput;
import lighthouse.ui.scene.view.lighthouseapi.LighthouseDisplay;
import lighthouse.ui.scene.viewmodel.LighthouseViewModel;
import lighthouse.util.LighthouseConstants;
import lighthouse.util.Listener;

/**
 * The remote Lighthouse view that uses the API to draw a
 * color grid on the actual highriser.
 */
public class RemoteLighthouseView implements LighthouseView {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteLighthouseView.class);
	private static final int LIGHTHOUSE_BYTES = LighthouseConstants.ROWS * LighthouseConstants.COLS * 3; // RGB colors
	private final LighthouseDisplay api;

	public RemoteLighthouseView(String username, String token) {
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

	public void addButtonInput(SceneLighthouseInput input) {
		api.addButtonListener(input);
	}
	
	public void removeButtonInput(SceneLighthouseInput input) {
		api.removeButtonListener(input);
	}
	
	public void addConnectListener(Listener<Void> listener) {
		api.addConnectListener(listener);
	}
	
	public void removeConnectListener(Listener<Void> listener) {
		api.removeConnectListener(listener);
	}
	
	@Override
	public void draw(LighthouseViewModel viewModel) {
		try {
			api.send(encode(viewModel));
		} catch (IOException e) {
			LOG.error("An IOException occurred while sending the grid to the Lighthouse: ", e);
		}
	}
	
	/** Encodes the colored grid in a byte array. */
	private byte[] encode(LighthouseViewModel viewModel) {
		if (viewModel.getRows() != LighthouseConstants.ROWS) {
			throw new IllegalArgumentException("Colored grid has " + viewModel.getRows() + " rows, but should have " + LighthouseConstants.ROWS);
		} else if (viewModel.getColumns() != LighthouseConstants.COLS) {
			throw new IllegalArgumentException("Colored grid has " + viewModel.getColumns() + " columns, but should have " + LighthouseConstants.COLS);
		}
		
		byte[] data = new byte[LIGHTHOUSE_BYTES];
		int i = 0;
		
		viewModel.render();
		
		for (int y = 0; y < LighthouseConstants.ROWS; y++) {
			for (int x = 0; x < LighthouseConstants.COLS; x++) {
				Color cell = viewModel.getColorAt(x, y);
				data[i] = (byte) cell.getRed();
				data[i + 1] = (byte) cell.getGreen();
				data[i + 2] = (byte) cell.getBlue();
				i += 3;
			}
		}
		
		return data;
	}
}
