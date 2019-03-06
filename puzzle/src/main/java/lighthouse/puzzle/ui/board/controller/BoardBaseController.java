package lighthouse.puzzle.ui.board.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Brick;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.ColorUtils;
import lighthouse.util.Direction;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

/**
 * A base implementation of {@link BoardResponder}
 * that provides common functionality for controllers.
 */
public abstract class BoardBaseController implements BoardResponder {
	private static final Logger LOG = LoggerFactory.getLogger(BoardBaseController.class);
	private final SceneInteractionFacade sceneFacade;
	private BoardViewModel viewModel;
	private boolean resetEnabled = false;
	
	public BoardBaseController(BoardViewModel viewModel, SceneInteractionFacade sceneFacade) {
		this.viewModel = viewModel;
		this.sceneFacade = sceneFacade;
	}
	
	protected boolean isResetEnabled() { return resetEnabled; }
	
	protected void setResetEnabled(boolean resetEnabled) { this.resetEnabled = resetEnabled; }
	
	protected BoardViewModel getViewModel() { return viewModel; }
	
	protected void update() {
		sceneFacade.update();
	}
	
	protected boolean isAllowed(Board board) {
		for (Board blockedPattern : viewModel.getBlockedStates()) {
			if (board.containsPattern(blockedPattern)) {
				return false;
			}
		}
		return true;
	}
	
	protected SceneInteractionFacade getSceneFacade() {
		return sceneFacade;
	}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	@Override
	public boolean reset() {
		if (resetEnabled) {
			LOG.debug("Resetting");
			getViewModel().clear();
			update();
			return true;
		} else {
			return false;
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
		DoubleVec selectedCenter = selectedBrick.getCenterPos();
		IntVec nextPos = gridPos;
		
		LOG.info("Selecting {} from ({}, {})", dir, selectedMin, selectedMax);
		
		if (selectedBrick != null) {
			Brick match = viewModel.getBricks()
				.stream()
				.filter(brick -> {
					IntVec minPos = brick.getMinPos();
					IntVec maxPos = brick.getMaxPos();
					
					if (LOG.isDebugEnabled()) {
						LOG.debug("For {} brick at ({}, {})", ColorUtils.describe(brick.getColor()), minPos, maxPos);
					}
					
					switch (dir) {
						case LEFT: return maxPos.getX() < selectedMin.getX();
						case UP: return maxPos.getY() < selectedMin.getY();
						case RIGHT: return selectedMax.getX() < minPos.getX();
						case DOWN: return selectedMax.getY() < minPos.getY();
						default: throw new IllegalStateException("Invalid direction " + dir);
					}
				})
				.min(Comparator.comparingDouble(brick -> selectedCenter.sub(brick.getCenterPos()).length()))
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
