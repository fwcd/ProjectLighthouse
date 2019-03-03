package lighthouse.ui.board.controller;

import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.util.IntVec;

/**
 * A responder that delegates to another responder. Can easily be swapped out.
 */
public class DelegateResponder implements BoardResponder {
	private BoardResponder delegate;
	
	public DelegateResponder(BoardResponder delegate) {
		this.delegate = delegate;
	}
	
	public BoardResponder getDelegate() {
		return delegate;
	}
	
	public void setDelegate(BoardResponder delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void press(IntVec gridPos) { delegate.press(gridPos); }
	
	@Override
	public void rightPress(IntVec gridPos) { delegate.rightPress(gridPos); }
	
	@Override
	public void dragTo(IntVec gridPos) { delegate.dragTo(gridPos); }
	
	@Override
	public void release(IntVec gridPos) { delegate.release(gridPos); }
			
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
	public void deselect() { delegate.deselect(); }
	
	@Override
	public void reset() { delegate.reset(); }
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) { delegate.updateViewModel(viewModel); }
}
