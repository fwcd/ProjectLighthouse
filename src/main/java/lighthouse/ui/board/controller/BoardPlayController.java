package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Edge;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.floating.FloatingContext;
import lighthouse.util.IntVec;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardPlayController.class);
	
	private final FloatingContext floatingCtx;
	private final CoordinateMapper coordinateMapper;
	private final Map<Direction, Integer> limits = new HashMap<>();
	private Board board;
	private boolean dragEvent;
	
	private IntVec floatingOffset;
	private IntVec startGridPos;
	private Brick brick;

	public BoardPlayController(Board model, FloatingContext floatingCtx, CoordinateMapper coordinateMapper) {
		this.floatingCtx = floatingCtx;
		this.coordinateMapper = coordinateMapper;
		
		board = model;
		resetLimits();
	}

	public void resetLimits() {
		limits.put(Direction.UP, Integer.MAX_VALUE);
		limits.put(Direction.DOWN, Integer.MAX_VALUE);
		limits.put(Direction.RIGHT, Integer.MAX_VALUE);
		limits.put(Direction.LEFT, Integer.MAX_VALUE);
	}
	
	private void computeLimits() {
		resetLimits();
		List<Edge> edgeList = brick.getEdges();
		for (Direction dir : Direction.values()) {
			edgeList.stream().filter(edge -> edge.getDir().getIndex() == dir.getIndex()).forEach(edge -> {
				IntVec face = brick.getPos().add(edge.getOff()).add(dir);
				int limit = 0;
				while (!board.hasBrickAt(face) && face.xIn(0, board.getColumns()) && face.yIn(0, board.getRows())) {
					limit += 1;
					face = face.add(dir);
				}
				if (limits.get(dir) > limit) {
					LOG.debug("Looking {} I found a smaller limit than {}: {}", dir, limits.get(dir), limit);
					limits.put(dir, limit);
				}
				if (limit > 0) {
					edge.setHighlighted(true);
				}
			});
		}
		LOG.debug("Limits: {}", limits);
	}
	
	@Override
	public void press(IntVec gridPos) {
		brick = board.locateBrick(gridPos);
		if (brick == null) return;
		dragEvent = true;
		startGridPos = gridPos;
		computeLimits();
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (!dragEvent) return;
		if (!gridPos.equals(startGridPos)) {
			IntVec at = gridPos.sub(startGridPos);
			List<Direction> atDirs = at.nearestDirections();

			atDirs.forEach(atDir -> {
				if (limits.get(atDir) > 0) {
					limits.put(atDir, limits.get(atDir) - 1);
					Brick newBrick = brick.movedInto(atDir);
					board.replace(brick, newBrick);
					brick = newBrick;
					startGridPos = startGridPos.add(atDir);
					computeLimits();
				}
			});
		}
	}
	
	@Override
	public void release(IntVec gridPos) {
		if (dragEvent != true) return;
		resetLimits();
		for (Edge edge : brick.getEdges()) {
			edge.setHighlighted(false);
		}
		dragEvent = false;
	}
	
	@Override
	public void floatingPress(IntVec pixelPos) {
		if (brick != null) {
			IntVec brickPixelPos = coordinateMapper.toPixelPos(brick.getPos());
			
			floatingOffset = pixelPos.sub(brickPixelPos);
			floatingCtx.setBlock(brick);
			floatingCtx.setPixelPos(pixelPos.sub(floatingOffset));
		}
	}
	
	@Override
	public void floatingDragTo(IntVec pixelPos) {
		if (floatingOffset != null) {
			floatingCtx.setPixelPos(pixelPos.sub(floatingOffset));
		}
	}
	
	@Override
	public void floatingRelease(IntVec pixelPos) {
		floatingCtx.clear();
		floatingOffset = null;
	}
	
	@Override
	public void updateBoard(Board board) {
		this.board = board;
	}
}
