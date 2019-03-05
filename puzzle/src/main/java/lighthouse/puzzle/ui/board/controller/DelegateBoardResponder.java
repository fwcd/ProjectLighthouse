package lighthouse.puzzle.ui.board.controller;

import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;

/**
 * A responder that delegates to another responder. Can easily be swapped out.
 */
public class DelegateBoardResponder implements BoardResponder {
	private BoardResponder delegate;
	
	public DelegateBoardResponder(BoardResponder delegate) {
		this.delegate = delegate;
	}
	
	public BoardResponder getDelegate() { return delegate; }
	
	public void setDelegate(BoardResponder delegate) { this.delegate = delegate; }
	
	@Override
	public boolean press(IntVec gridPos) { return delegate.press(gridPos); }
	
	@Override
	public boolean rightPress(IntVec gridPos) { return delegate.rightPress(gridPos); }
	
	@Override
	public boolean dragTo(IntVec gridPos) { return delegate.dragTo(gridPos); }
	
	@Override
	public boolean release(IntVec gridPos) { return delegate.release(gridPos); }
			
	@Override
	public IntVec selectAny() { return delegate.selectAny(); }
	
	@Override
	public IntVec select(IntVec gridPos) { return delegate.select(gridPos); }
	
	@Override
	public IntVec selectUp(IntVec gridPos) { return delegate.selectUp(gridPos); }
	
	@Override
	public IntVec selectLeft(IntVec gridPos) { return delegate.selectLeft(gridPos); }
	
	@Override
	public IntVec selectDown(IntVec gridPos) { return delegate.selectDown(gridPos); }
	
	@Override
	public IntVec selectRight(IntVec gridPos) { return delegate.selectRight(gridPos); }
	
	@Override
	public boolean deselect() { return delegate.deselect(); }
	
	@Override
	public boolean reset() { return delegate.reset(); }
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) { delegate.updateViewModel(viewModel); }
}
