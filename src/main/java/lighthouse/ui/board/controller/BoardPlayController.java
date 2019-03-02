package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Edge;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardPlayController.class);
	private Map<Direction, Integer> limits;
	private Updatable updater;
	private BoardViewModel viewModel;
	private boolean dragEvent;
	
	private IntVec startGridPos;
	private Brick brick;

	public BoardPlayController(BoardViewModel viewModel, Updatable updater) {
		this.updater = updater;
		this.viewModel = viewModel;
		resetLimits();
	}
	
	private void resetLimits() {
		limits = new HashMap<>();
		for (Direction direction : Direction.values()) {
			limits.put(direction, Integer.MAX_VALUE);
		}
	}
	
	private void computeLimits() {
		limits = viewModel.getLimitsFor(brick);
	}
	
	@Override
	public void press(IntVec gridPos) {
		brick = viewModel.locateBrick(gridPos);
		if (brick == null) return;
		dragEvent = true;
		startGridPos = gridPos;
		computeLimits();
		updater.update();
		
		LOG.info("Possible moves: {}", viewModel.streamPossibleMovesFor(brick).collect(Collectors.toList()));
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
					viewModel.replace(brick, newBrick);
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
	public void updateViewModel(BoardViewModel viewModel) {
		this.viewModel = viewModel;
	}
}
