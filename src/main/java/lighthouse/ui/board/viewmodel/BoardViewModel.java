package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.GameBlock;
import lighthouse.model.grid.ColorGrid;
import lighthouse.util.IntVec;

/**
 * A UI-independent representation of the
 * board together with its presentation
 * artifacts (such as overlays and animations).
 */
public class BoardViewModel implements ColorGrid {
	private final Board model;
	private final BoardEditState editState = new BoardEditState();
	
	public BoardViewModel(Board model) {
		this.model = model;
	}
	
	public Color getColorAt(IntVec gridPos) {
		Color color = model.getColorAt(gridPos);
		
		if (color == null) {
			GameBlock bip = editState.getBrickInProgress();
			if (bip != null) {
				color = bip.getColor();
			}
		}
		
		return (color == null) ? Color.BLACK : color;
	}
	/** Fetches the current editing state of the board. */
	public BoardEditState getEditState() { return editState; }
	
	public void clear() {
		model.clear();
		editState.reset();
	}
	
	// === Delegated methods ===
	
	public boolean hasBrickAt(IntVec gridPos) { return model.hasBrickAt(gridPos); }
	
	public Brick removeBrickAt(IntVec gridPos) { return model.removeBrickAt(gridPos); }
	
	public void add(Brick brick) { model.add(brick); }
	
	public Map<Direction, Integer> getLimitsFor(Brick brick) { return model.getLimitsFor(brick); }
	
	public Brick locateBrick(IntVec gridPos) { return model.locateBrick(gridPos); }
	
	public void replace(Brick oldBrick, Brick newBrick) { model.replace(oldBrick, newBrick); }
	
	public Collection<? extends Brick> getBricks() { return model.getBricks(); }
}
