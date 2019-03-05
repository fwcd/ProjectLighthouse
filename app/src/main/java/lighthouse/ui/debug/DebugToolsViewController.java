package lighthouse.ui.debug;

import javax.swing.JComponent;

import com.alee.laf.tabbedpane.WebTabbedPane;

import lighthouse.model.AppModel;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.debug.animations.ActiveAnimationsViewController;
import lighthouse.ui.debug.listeners.ListenerGraphViewController;

/**
 * Manages useful tools for debugging.
 */
public class DebugToolsViewController implements SwingViewController, AutoCloseable {
	private final WebTabbedPane component;
	private final ListenerGraphViewController listenerGraph;
	private final ActiveAnimationsViewController activeAnimations;
	
	public DebugToolsViewController(AppModel appModel, AnimationTracker animationTracker) {
		component = new WebTabbedPane();
		
		listenerGraph = new ListenerGraphViewController(appModel);
		component.addTab("Listeners", listenerGraph.getComponent());
		
		if (animationTracker != null) {
			activeAnimations = new ActiveAnimationsViewController(animationTracker);
			component.addTab("Active Animations", activeAnimations.getComponent());
		} else {
			activeAnimations = null;
		}
	}
	
	@Override
	public void close() {
		if (activeAnimations != null) {
			activeAnimations.close();
		}
		listenerGraph.removeUpdateHooks();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
