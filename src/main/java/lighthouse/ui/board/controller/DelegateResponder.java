package lighthouse.ui.board.controller;

/**
 * A responder that delegates to another responder.
 * Can easily be swapped out.
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
	public void press(int gridX, int gridY) {
		delegate.press(gridX, gridY);
	}
	
	@Override
	public void dragTo(int gridX, int gridY) {
		delegate.dragTo(gridX, gridY);
	}
	
	@Override
	public void release(int gridX, int gridY) {
		delegate.release(gridX, gridY);
	}
}
