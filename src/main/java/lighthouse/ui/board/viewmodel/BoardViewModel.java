package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.GameBlock;
import lighthouse.model.Move;
import lighthouse.model.grid.ColorGrid;
import lighthouse.ui.board.viewmodel.overlay.Overlay;
import lighthouse.util.IntVec;

/**
 * A UI-independent representation of the
 * board together with its presentation
 * artifacts (such as overlays and animations).
 */
public class BoardViewModel implements ColorGrid {
	private final Board model;
	private final BoardEditState editState = new BoardEditState();
	private final BoardStatistics statistics;
	private final List<Overlay> overlays = new ArrayList<>();
	private Integer selectedID = null;
	
	public BoardViewModel(Board model) {
		this(model, new BoardStatistics());
	}
	
	public BoardViewModel(Board model, BoardStatistics statistics) {
		this.model = model;
		this.statistics = statistics;
	}
	
	@Override
	public Color getColorAt(IntVec gridPos) {
		GameBlock block = locateBlock(gridPos);
		return (block == null) ? Color.BLACK : block.getColor();
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
	
	public GameBlock locateBlock(IntVec gridPos) {
		GameBlock block = model.locateBrick(gridPos);
		
		if (block == null) {
			GameBlock bip = editState.getBrickInProgress();
			if (bip != null && bip.contains(gridPos)) {
				block = bip;
			}
		}
		
		return block;
	}
	
	/** Fetches the current editing state of the board. */
	public BoardEditState getEditState() { return editState; }
	
	/** Fetches statistics about the board state. */
	public BoardStatistics getStatistics() { return statistics; }
	
	public void clear() {
		model.clear();
		editState.reset();
	}
	
	public boolean isSelected(GameBlock block) { return (selectedID != null) && (block != null) && block.getID() == selectedID; }
	
	public boolean selectAt(IntVec gridPos) {
		Brick brick = locateBrick(gridPos);
		if (brick != null) {
			select(brick);
			return true;
		} else {
			return false;
		}
	}
	
	public void select(Brick brick) { selectedID = brick.getID(); }
	
	public void deselect() { selectedID = null; }
	
	public List<? extends Overlay> getOverlays() { return overlays; }
	
	public void addOverlay(Overlay overlay) { overlays.add(overlay); } 
	
	public void removeOverlay(Overlay overlay) { overlays.remove(overlay); }
	
	public Board getModel() { return model; }
	
	// === Delegated methods ===
	
	public boolean hasBrickAt(IntVec gridPos) { return model.hasBrickAt(gridPos); }
	
	public Brick removeBrickAt(IntVec gridPos) { return model.removeBrickAt(gridPos); }
	
	public void add(Brick brick) { model.add(brick); }
	
	public Map<Direction, Integer> getLimitsFor(Brick brick) { return model.getLimitsFor(brick); }
	
	public Brick locateBrick(IntVec gridPos) { return model.locateBrick(gridPos); }
	
	public void replace(Brick oldBrick, Brick newBrick) { model.replace(oldBrick, newBrick); }
	
	public Collection<? extends Brick> getBricks() { return model.getBricks(); }
	
	public Stream<Move> streamPossibleMoves() { return model.streamPossibleMoves(); }
	
	public Stream<Move> streamPossibleMovesFor(Brick brick) { return model.streamPossibleMovesFor(brick); }
}
