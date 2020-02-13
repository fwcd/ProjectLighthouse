package lighthouse.ui.scene.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.util.IntVec;

/**
 * A responder that delegates to another responder. Can easily be swapped out.
 */
public class DelegateResponder implements SceneResponder {
	private static final Logger LOG = LoggerFactory.getLogger(DelegateResponder.class);
	private SceneResponder delegate;
	
	public DelegateResponder(SceneResponder delegate) {
		this.delegate = delegate;
	}
	
	public SceneResponder getDelegate() { return delegate; }
	
	public void setDelegate(SceneResponder delegate) {
		this.delegate = delegate;
		LOG.info("Setting responder delegate to {}", delegate);
	}
	
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
	public IntVec up(IntVec gridPos) { return delegate.up(gridPos); }
	
	@Override
	public IntVec left(IntVec gridPos) { return delegate.left(gridPos); }
	
	@Override
	public IntVec down(IntVec gridPos) { return delegate.down(gridPos); }
	
	@Override
	public IntVec right(IntVec gridPos) { return delegate.right(gridPos); }
	
	@Override
	public boolean deselect() { return delegate.deselect(); }
	
	@Override
	public boolean reset() { return delegate.reset(); }
	
	@Override
	public boolean fire() { return delegate.fire(); }
}
