package lighthouse.ui.view;

import lighthouse.model.Grid;
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

/**
 * A remote (Lighthouse) view.
 */
public class LighthouseGridView implements GridView {
	private final LighthouseDisplay api;
	
	public LighthouseGridView(Grid model) {
		ConfigFile authentication = new ResourceConfigFile("/authentication.txt");
		if (authentication.has("username") && authentication.has("token")) {
			api = new LighthouseDisplay(authentication.get("username"), authentication.get("token"));
		} else {
			throw new IllegalStateException("Authentication config did not contain both username and token");
		}
		
		// TODO: Better view API, instead of injecting model pass the color array through the view interface?
	}
}
