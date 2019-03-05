package lighthouse.ui.debug.animations;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.ViewController;
import lighthouse.puzzle.ui.board.debug.AnimationTracker;
import lighthouse.ui.util.LayoutUtils;

public class ActiveAnimationsViewController implements ViewController, AutoCloseable {
	private final JPanel component;
	private final AnimationTracker tracker;
	private final ActiveAnimationsView view;
	
	public ActiveAnimationsViewController(AnimationTracker tracker) {
		this.tracker = tracker;
		
		component = new JPanel(new BorderLayout());
		component.add(LayoutUtils.buttonOf("Enable Animation Tracker", this::enableTracker), BorderLayout.NORTH);
		
		view = new ActiveAnimationsView(tracker);
		component.add(view.getComponent());
	}
	
	public void enableTracker() {
		tracker.setEnabled(true);
	}
	
	@Override
	public void close() {
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
