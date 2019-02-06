package lighthouse.view.remote;

import lighthouse.view.GridView;
import lighthouse.view.GridViewResponder;

/**
 * A remote (Lighthouse) view.
 */
public class LighthouseGridView implements GridView {
	@Override
	public void addResponder(GridViewResponder responder) {
		// Do nothing, since the Lighthouse does not provide
		// a way to fire events
	}
}
