package lighthouse.ui.board.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.ColorUtils;
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
		if (viewModel.selectAt(gridPos)) {
			update();
			return gridPos;
		} else {
			return null;
		}
	}
	
	@Override
	public IntVec selectAny() {
		Collection<? extends Brick> bricks = viewModel.getBricks();
		if (bricks.isEmpty()) {
			return null;
		} else {
			Brick brick = bricks.stream()
				.max(Comparator.comparingDouble(it -> it.getMaxPos().length()))
				.orElseThrow(NoSuchElementException::new);
			viewModel.select(brick);
			update();
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
		
		if (selectedBrick == null) {
			return gridPos;
		}
		
		IntVec selectedMax = selectedBrick.getMaxPos();
		IntVec selectedMin = selectedBrick.getMinPos();
		IntVec nextPos = gridPos;
		
		LOG.info("Selecting {} from ({}, {})", dir, selectedMin, selectedMax);
		
		if (selectedBrick != null) {
			Brick match = viewModel.getBricks()
				.stream()
				.filter(brick -> {
					IntVec minPos = brick.getMinPos();
					IntVec maxPos = brick.getMaxPos();
					
					if (LOG.isDebugEnabled()) {
						LOG.debug("For {} brick at {}: brickMin = {}, brickMax = {}", ColorUtils.describe(brick.getColor()), brick.getPos(), minPos, maxPos);
					}
					
					switch (dir) {
						case LEFT: return selectedMax.getX() < minPos.getX();
						case UP: return selectedMax.getY() < minPos.getY();
						case RIGHT: return maxPos.getX() < selectedMin.getX();
						case DOWN: return maxPos.getY() < selectedMin.getY();
						default: throw new IllegalStateException("Invalid direction " + dir);
					}
				})
				.min(Comparator.comparingInt(brick -> Math.abs(dir.isLeftOrRight() ? (gridPos.getX() - brick.getPos().getX()) : (gridPos.getY() - brick.getPos().getY()))))
				.orElse(null);
			
			if (match != null) {
				viewModel.select(match);
				update();
				nextPos = match.getPos();
			}
		}
		
		LOG.debug(" -> {}", nextPos);
		
		return nextPos;
	}
}
