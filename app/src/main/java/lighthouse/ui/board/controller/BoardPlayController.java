package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.util.Direction;
import lighthouse.model.Edge;
import lighthouse.ui.board.BoardAnimationRunner;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController extends BoardBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BoardPlayController.class);
	private final BoardAnimationRunner animationRunner;
	
	private Map<Direction, Integer> limits;
	private boolean dragEvent;
	private Direction lastDir;
	
	private IntVec startGridPos;
	private Brick brick;

	public BoardPlayController(BoardViewModel viewModel, Updatable updater, BoardAnimationRunner animationRunner) {
		super(viewModel, updater);
		this.animationRunner = animationRunner;
		
		resetLimits();
		setResetEnabled(true);
	}
	
	private void resetLimits() {
		limits = new HashMap<>();
		for (Direction direction : Direction.values()) {
			limits.put(direction, Integer.MAX_VALUE);
		}
	}
	
	private void computeLimits() {
		limits = getViewModel().getLimitsFor(brick);
	}
	
	@Override
	public boolean press(IntVec gridPos) {
		brick = getViewModel().locateBrick(gridPos);
		if (brick == null) return false;
		dragEvent = true;
		startGridPos = gridPos;
		computeLimits();
		update();
		
		LOG.info("Possible moves: {}", getViewModel().streamPossibleMovesFor(brick).collect(Collectors.toList()));
		return true;
	}
	
	@Override
	public boolean dragTo(IntVec gridPos) {
		if (!dragEvent) return false;
		if (!gridPos.equals(startGridPos)) {
			IntVec at = gridPos.sub(startGridPos);
			List<Direction> atDirs = at.nearestDirections();
			boolean updated = false;
			
			for (Direction atDir : atDirs) {
				if (limits.get(atDir) > 0) {
					limits.put(atDir, limits.get(atDir) - 1);
					
					Brick newBrick = brick.movedInto(atDir);
					Board nextBoard = getViewModel().getModel().copy();
					nextBoard.replace(brick, newBrick);
					
					if (isAllowed(nextBoard)) {
						getViewModel().transitionTo(nextBoard);
					
						if (lastDir == null || !atDir.equals(lastDir)) {
							// Only track moves into different directions
							getViewModel().getStatistics().incrementMoveCount();
							lastDir = atDir;
						}
						
						brick = newBrick;
						startGridPos = startGridPos.add(atDir);
						computeLimits();
						updated = true;
					}
				}
			}
			update();
			return updated;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean release(IntVec gridPos) {
		if (dragEvent != true) return false;
		resetLimits();
		for (Edge edge : brick.getEdges()) {
			edge.setHighlighted(false);
		}
		dragEvent = false;
		update();
		return true;
	}
}
