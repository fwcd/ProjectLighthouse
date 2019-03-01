package lighthouse.ui.board.viewmodel;

import java.awt.Color;

import lighthouse.model.Board;
import lighthouse.model.grid.ColorGrid;
import lighthouse.util.IntVec;

/**
 * A UI-independent representation of the
 * board together with its presentation
 * artifacts (such as overlays and animations).
 */
public class BoardViewModel implements ColorGrid {
	private final Board model;
	
	public BoardViewModel(Board model) {
		this.model = model;
	}
	
	public Board getModel() { return model; }
	
	public Color getColorAt(IntVec gridPos) {
		return model.getColorAt(gridPos);
	}
}
