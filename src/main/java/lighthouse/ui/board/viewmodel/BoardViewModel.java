package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

/**
 * A UI-independent representation of the
 * board together with its presentation
 * artifacts (such as overlays and animations).
 */
public class BoardViewModel implements ColorGrid {
	private static final int FRAMES_PER_BOARD_TRANSITION = 120;
	private final TransitionableBoard transitionableModel;
	private final BoardEditState editState = new BoardEditState();
	private final BoardStatistics statistics;
	private final List<Overlay> overlays = new ArrayList<>();
	private List<Board> blockedStates;
	private Integer selectedID = null;
	
	public BoardViewModel(Board model) {
		this(model, Collections.emptyList());
	}
	
	public BoardViewModel(Board model, List<Board> blockedStates) {
		this(model, blockedStates, new BoardStatistics());
	}
	
	public BoardViewModel(Board model, List<Board> blockedStates, BoardStatistics statistics) {
		this.blockedStates = blockedStates;
		this.statistics = statistics;
		transitionableModel = new TransitionableBoard(model, FRAMES_PER_BOARD_TRANSITION);
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
		GameBlock block = getModel().locateBrick(gridPos);
		
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
		getModel().clear();
		editState.reset();
	}
	
	public boolean isSelected(GameBlock block) { return (selectedID != null) && (block != null) && block.getID() == selectedID; }
	
	public Integer getSelectedID() { return selectedID; }
	
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
	
	public Board getModel() { return transitionableModel.getCurrentBoard(); }
	
	public List<? extends Board> getBlockedStates() { return blockedStates; }
	
	public void setBlockedStates(List<Board> blockedStates) { this.blockedStates = blockedStates; }
	
	// TODO: Being able to pass different kinds of interpolations and total frame counts here
	public void transitionTo(Board next) { transitionableModel.enqueueTransition(next); }
	
	public boolean hasNextTransitionFrame() { return transitionableModel.hasNextFrame(); }
	
	public void nextTransitionFrame() { transitionableModel.nextFrame(); }
	
	public DoubleVec transitionedGridPosForBrick(Brick brick) { 
		return transitionableModel.gridPosForBrick(brick)
			.orElseGet(() -> brick.getPos().toDouble());
	}
	
	// === Delegated methods ===
	
	public boolean hasBrickAt(IntVec gridPos) { return getModel().hasBrickAt(gridPos); }
	
	public Brick removeBrickAt(IntVec gridPos) { return getModel().removeBrickAt(gridPos); }
	
	public void add(Brick brick) { getModel().add(brick); }
	
	public Map<Direction, Integer> getLimitsFor(Brick brick) { return getModel().getLimitsFor(brick); }
	
	public Brick locateBrick(IntVec gridPos) { return getModel().locateBrick(gridPos); }
	
	public void replace(Brick oldBrick, Brick newBrick) { getModel().replace(oldBrick, newBrick); }
	
	public Collection<? extends Brick> getBricks() { return getModel().getBricks(); }
	
	public Stream<Move> streamPossibleMoves() { return getModel().streamPossibleMoves(); }
	
	public Stream<Move> streamPossibleMovesFor(Brick brick) { return getModel().streamPossibleMovesFor(brick); }
}
