package lighthouse.ui.board.controller;

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
	public void press(IntVec gridPos) {
		delegate.press(gridPos);
	}
	
	@Override
	public void dragTo(IntVec gridPos) {
		delegate.dragTo(gridPos);
	}
	
	@Override
	public void release(IntVec gridPos) {
		delegate.release(gridPos);
	}
}
