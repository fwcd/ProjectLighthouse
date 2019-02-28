package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Edge;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController implements BoardResponder {
	private Map<Direction, Integer> limits;
	private Updatable updater;
	private Board board;
	private boolean dragEvent;
	
	private IntVec startGridPos;
	private Brick brick;

	public BoardPlayController(Board model, Updatable updater) {
		this.updater = updater;
		board = model;
		resetLimits();
	}
	
	private void resetLimits() {
		limits = new HashMap<>();
		for (Direction direction : Direction.values()) {
			limits.put(direction, Integer.MAX_VALUE);
		}
	}
	
	private void computeLimits() {
		limits = board.getLimitsFor(brick);
	}
	
	@Override
	public void press(IntVec gridPos) {
		brick = board.locateBrick(gridPos);
		if (brick == null) return;
		dragEvent = true;
		startGridPos = gridPos;
		computeLimits();
		updater.update();
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
			updater.update();
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
		updater.update();
	}
	
	@Override
	public void updateBoard(Board board) {
		this.board = board;
	}

	public Brick getCurrentBrick(){
		return brick;
	}

	public void setCurrentBrick(Brick brick){
		this.brick = brick;
		computeLimits();
	}

	public void setCurrentBoard(Board board){
		this.board = board;
	}

	public Map<Direction, Integer> getCurrentLimits(){
		return limits;
	}
}
