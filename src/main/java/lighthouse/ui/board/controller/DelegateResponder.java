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
	public void select(IntVec gridPos) { delegate.select(gridPos); }
	
	@Override
	public void deselect(IntVec gridPos) { delegate.deselect(gridPos); }
	
	@Override
	public void reset() { delegate.reset(); }
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) { delegate.updateViewModel(viewModel); }
}
