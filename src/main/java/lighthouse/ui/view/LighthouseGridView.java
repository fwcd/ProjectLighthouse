package lighthouse.ui.view;

import java.awt.Color;

/**
 * The remote Lighthouse view that uses the API to draw a color grid on the
 * actual highriser.
 */
public class LighthouseGridView implements GridView {
	private final LighthouseDisplay api;

	public LighthouseGridView(String username, String token) {
		api = new LighthouseDisplay(username, token);
	}

	public void connect() {
		try {
			api.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Color[][] grid) {
		// TODO
	}
}
