package lighthouse.ui.board.controller;

import java.util.Collection;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.Updatable;

/**
 * A base implementation of {@link BoardResponder}
 * that provides common functionality for controllers.
 */
public abstract class BoardBaseController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardBaseController.class);
	private final Updatable updater;
	private BoardViewModel viewModel;
	private boolean resetEnabled = false;
	
	public BoardBaseController(BoardViewModel viewModel, Updatable updater) {
		this.viewModel = viewModel;
		this.updater = updater;
	}
	
	protected boolean isResetEnabled() { return resetEnabled; }
	
	protected void setResetEnabled(boolean resetEnabled) { this.resetEnabled = resetEnabled; }
	
	protected BoardViewModel getViewModel() { return viewModel; }
	
	protected void update() {
		updater.update();
	}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	@Override
	public void reset() {
		if (resetEnabled) {
			LOG.debug("Resetting");
			getViewModel().clear();
			update();
		}
	}
	
	@Override
	public IntVec select(IntVec gridPos) {
		return viewModel.selectAt(gridPos) ? gridPos : null;
	}
	
	@Override
	public IntVec selectAny() {
		Collection<? extends Brick> bricks = viewModel.getBricks();
		if (bricks.isEmpty()) {
			return null;
		} else {
			Brick brick = bricks.iterator().next();
			viewModel.select(brick);
			return brick.getPos();
		}
	}
	
	@Override
	public IntVec selectUp(IntVec gridPos) {
		return selectInto(Direction.UP, gridPos);
	}
	
	@Override
	public IntVec selectLeft(IntVec gridPos) {
		return selectInto(Direction.LEFT, gridPos);
	}
	
	@Override
	public IntVec selectDown(IntVec gridPos) {
		return selectInto(Direction.DOWN, gridPos);
	}
	
	@Override
	public IntVec selectRight(IntVec gridPos) {
		return selectInto(Direction.RIGHT, gridPos);
	}
	
	public IntVec selectInto(Direction dir, IntVec gridPos) {
		Brick selectedBrick = viewModel.locateBrick(gridPos);
		IntVec selectedMax = selectedBrick.getMaxPos();
		IntVec selectedMin = selectedBrick.getMinPos();
		
		if (selectedBrick != null) {
			Brick match = viewModel.getBricks()
				.stream()
				.filter(brick -> {
					switch (dir) {
						case LEFT: return selectedMax.getX() < brick.getMinPos().getX();
						case UP: return selectedMax.getY() < brick.getMinPos().getY();
						case RIGHT: return brick.getMaxPos().getX() < selectedMin.getX();
						case DOWN: return brick.getMaxPos().getY() < selectedMin.getY();
						default: throw new IllegalStateException("Invalid direction " + dir);
					}
				})
				.min(Comparator.comparingInt(brick -> Math.abs(dir.isLeftOrRight() ? (gridPos.getX() - brick.getPos().getX()) : (gridPos.getY() - brick.getPos().getY()))))
				.orElse(null);
			
			if (match != null) {
				viewModel.select(match);
				update();
				return match.getPos();
			}
		}
		
		return null;
	}
}
