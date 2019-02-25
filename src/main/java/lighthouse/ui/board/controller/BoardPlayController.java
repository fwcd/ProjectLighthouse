package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Edge;
import lighthouse.util.IntVec;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController implements BoardResponder {
	private final Board board;
	private boolean dragEvent;
	private final Map<Direction, Integer> limits = new HashMap<>();

	private IntVec start;
	private Brick brick;

	public BoardPlayController(Board model) {
		board = model;
	}

	void resetLimits() {
		limits.put(Direction.UP, 99);
		limits.put(Direction.DOWN, 99);
		limits.put(Direction.RIGHT, 99);
		limits.put(Direction.LEFT, 99);
	}
	
	@Override
	public void press(IntVec gridPos) {
		brick = board.locateBrick(gridPos);
		if (brick == null) return;
		dragEvent = true;
		start = gridPos;
		List<Edge> edgeList = brick.getEdges();
		for (Direction dir : Direction.values()) {
			edgeList.stream().filter(edge -> edge.getDir().getIndex() == dir.getIndex()).forEach(edge -> {
				edge.setHighlighted(true);
				IntVec face = brick.getPos().add(edge.getOff()).add(dir);
				int limit = 0;
				while (board.locateBrick(face) == null && face.xIn(0, board.getColumns()) && face.yIn(0, board.getRows())) {
					limit += 1;
					face = face.add(dir);
				}
				if (limits.get(dir) > limit) limits.put(dir, limit);
			});
		}

	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		if (!dragEvent) return;
		if (!gridPos.equals(start)) {
			IntVec at = gridPos.sub(start);
			Direction atDir = at.nearestDirection();
			
			if (limits.get(atDir) > 0) {
				limits.put(atDir,limits.get(atDir) -1);
				brick.moveInto(atDir);
				start = gridPos;
			}
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
}
