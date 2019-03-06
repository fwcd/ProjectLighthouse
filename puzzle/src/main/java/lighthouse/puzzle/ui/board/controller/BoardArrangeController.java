package lighthouse.puzzle.ui.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Brick;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;

/**
 * A responder implementation that allows
 * the user to freely arrange bricks.
 */
public class BoardArrangeController extends BoardBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BoardArrangeController.class);
	private IntVec last;
	private boolean dragging = false;
	
	public BoardArrangeController(BoardViewModel viewModel, SceneInteractionFacade sceneFacade) {
		super(viewModel, sceneFacade);
	}
	
	@Override
	public boolean press(IntVec gridPos) {
		BoardViewModel viewModel = getViewModel();
		
		if (viewModel.hasBrickAt(gridPos)) {
			viewModel.getEditState().beginEdit(viewModel.removeBrickAt(gridPos));
			LOG.debug("Pressed at {}", gridPos);
			last = gridPos;
			dragging = true;
			update();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean dragTo(IntVec gridPos) {
		if (dragging) {
			IntVec delta = gridPos.sub(last);
			getViewModel().getEditState().moveBy(delta);
			last = gridPos;
			update();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean release(IntVec gridPos) {
		if (dragging) {
			LOG.debug("Released at {}", gridPos);
			
			BoardViewModel viewModel = getViewModel();
			Board nextBoard = viewModel.getModel().copy();
			Brick assembledBrick = viewModel.getEditState().finishEdit(gridPos);
			nextBoard.add(assembledBrick);
			
			boolean allowed = isAllowed(nextBoard);
			if (allowed) {
				viewModel.add(assembledBrick);
				last = null;
				dragging = false;
			}
			
			update();
			return allowed;
		}
		return false;
	}
}
