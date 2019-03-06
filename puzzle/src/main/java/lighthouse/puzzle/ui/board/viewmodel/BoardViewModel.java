package lighthouse.puzzle.ui.board.viewmodel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lighthouse.model.grid.ColorGrid;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Brick;
import lighthouse.puzzle.model.GameBlock;
import lighthouse.puzzle.model.Move;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneRect;
import lighthouse.ui.scene.viewmodel.graphics.SceneShape;
import lighthouse.ui.scene.viewmodel.graphics.Shading;
import lighthouse.util.Direction;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * A UI-independent representation of the
 * board together with its presentation
 * artifacts (such as overlays and animations).
 */
public class BoardViewModel implements ColorGrid, SceneLayer {
	private static final int FRAMES_PER_BOARD_TRANSITION = 10;
	private final TransitionableBoard transitionableModel;
	private final BoardEditState editState = new BoardEditState();
	private final BoardStatistics statistics;
	private final List<SceneLayer> overlays = new ArrayList<>();
	private List<Board> blockedStates;
	private Integer selectedID = null;
	
	private final ListenerList<Board> boardListeners = new ListenerList<>("BoardViewModel.boardListeners");
	
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
		return locateBlock(gridPos.toDouble());
	}
	
	public GameBlock locateBlock(DoubleVec gridPos) {
		GameBlock block = getModel().streamBricks()
			.filter(brick -> {
				DoubleVec brickOffset = transitionedGridPosForBrick(brick).sub(brick.getPos().toDouble());
				
				return brick.getAllPositions().stream()
					.map(IntVec::toDouble)
					.map(brickOffset::add)
					.anyMatch(brickPos -> {
						DoubleVec diff = gridPos.sub(brickPos);
						return diff.xIn(0, 1) && diff.yIn(0, 1);
					});
			})
			.findAny()
			.orElse(null);
			
		
		if (block == null) {
			GameBlock bip = editState.getBrickInProgress();
			if (bip != null && bip.contains(gridPos.floor())) {
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
	
	public List<? extends SceneLayer> getOverlays() { return overlays; }
	
	public void addOverlay(SceneLayer overlay) { overlays.add(overlay); } 
	
	public void removeOverlay(SceneLayer overlay) { overlays.remove(overlay); }
	
	public Board getModel() { return transitionableModel.getCurrentBoard(); }
	
	public List<? extends Board> getBlockedStates() { return blockedStates; }
	
	public void setBlockedStates(List<Board> blockedStates) { this.blockedStates = blockedStates; }
	
	// TODO: Being able to pass different kinds of interpolations and total frame counts here
	public void transitionTo(Board next) {
		transitionableModel.enqueueTransition(next);
		boardListeners.fire(next);
	}
	
	@Override
	public boolean hasNextTransitionFrame() { return transitionableModel.hasNextFrame(); }
	
	@Override
	public void nextTransitionFrame() { transitionableModel.nextFrame(); }
	
	public DoubleVec transitionedGridPosForBrick(Brick brick) { 
		return transitionableModel.gridPosForBrick(brick)
			.orElseGet(() -> brick.getPos().toDouble());
	}
	
	public ListenerList<Board> getBoardListeners() { return boardListeners; }
	
	@Override
	public List<SceneShape> getShapes() {
		List<SceneShape> shapes = new ArrayList<>();
		GameBlock brickInProgress = editState.getBrickInProgress();
		
		if (brickInProgress != null) {
			shapesOfBlock(brickInProgress).forEach(shapes::add);
		}
		
		getModel().streamBricks()
			.flatMap(this::shapesOfBrick)
			.forEach(shapes::add);
		
		return shapes;
	}
	
	private Stream<SceneShape> shapesOfBlock(GameBlock block) {
		return block.streamAllPositions()
			.map(IntVec::toDouble)
			.map(it -> new SceneRect(it, DoubleVec.ONE_ONE, block.getColor(), Shading.FILLED)); // TODO: Selection highlighting
	}
	
	private Stream<SceneShape> shapesOfBrick(Brick brick) {
		// return shapesOfBlock(brick);
		DoubleVec brickOffset = transitionedGridPosForBrick(brick).sub(brick.getPos().toDouble());
		return brick.streamAllPositions()
			.map(IntVec::toDouble)
			.map(brickOffset::add)
			.map(it -> new SceneRect(it, DoubleVec.ONE_ONE, brick.getColor(), Shading.FILLED)); // TODO: Selection highlighting
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
