package lighthouse.ui.board.controller;

import lighthouse.model.Board;
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
	public void reset() { delegate.reset(); }
	
	@Override
	public void floatingPress(IntVec pixelPos) { delegate.floatingPress(pixelPos); }
	
	@Override
	public void floatingDragTo(IntVec pixelPos) { delegate.floatingDragTo(pixelPos); }
	
	@Override
	public void floatingRelease(IntVec pixelPos) { delegate.floatingRelease(pixelPos); }
	
	@Override
	public void updateBoard(Board board) { delegate.updateBoard(board); }
}
