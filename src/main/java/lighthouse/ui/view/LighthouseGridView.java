package lighthouse.ui.view;

/**
 * A remote (Lighthouse) view.
 */
public class LighthouseGridView implements GridView {
	private final LighthouseDisplay api;
	
	public LighthouseGridView(String username, String token) {
		api = new LighthouseDisplay(username, token);
	}
}
