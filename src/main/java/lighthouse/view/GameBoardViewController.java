package lighthouse.view;

import java.util.ArrayList;
import java.util.List;

import lighthouse.model.GameBoard;

/**
 * Manages the GameBoard views and delegates actions.
 */
public class GameBoardViewController implements GridViewResponder {
	private final List<GridView> views = new ArrayList<>();
	
	public GameBoardViewController(GameBoard model) {
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
