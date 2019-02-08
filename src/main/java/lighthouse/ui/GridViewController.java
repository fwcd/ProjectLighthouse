package lighthouse.ui;

import java.util.ArrayList;
import java.util.List;

import lighthouse.model.Grid;

/**
 * Manages the GridViews and delegates actions.
 */
public class GridViewController implements GridViewResponder {
	private final List<GridView> views = new ArrayList<>();
	
	public GridViewController(Grid model) {
		// TODO
	}
	
	public void addView(GridView view) {
		views.add(view);
	}
	
	@Override
	public void mouseDown(int gridX, int gridY) {
		// TODO
	}
	
	@Override
	public void mouseDrag(int gridX, int gridY) {
		// TODO
	}
	
	@Override
	public void mouseUp(int gridX, int gridY) {
		// TODO
	}
}
