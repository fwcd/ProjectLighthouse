package lighthouse.ui.debug;

import javax.swing.JComponent;

import com.alee.laf.tabbedpane.WebTabbedPane;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.debug.animations.ActiveAnimationsViewController;
import lighthouse.ui.debug.listeners.ListenerGraphViewController;

/**
 * Manages useful tools for debugging.
 */
public class DebugToolsViewController implements ViewController, AutoCloseable {
	private final WebTabbedPane component;
	private final ListenerGraphViewController listenerGraph;
	private final ActiveAnimationsViewController activeAnimations;
	
	public DebugToolsViewController(AppModel appModel, GameViewController gameVC) {
		component = new WebTabbedPane();
		
		listenerGraph = new ListenerGraphViewController(appModel, gameVC);
		component.addTab("Listeners", listenerGraph.getComponent());
		
		activeAnimations = new ActiveAnimationsViewController(gameVC.getBoard().getAnimationTracker());
		component.addTab("Active Animations", activeAnimations.getComponent());
	}
	
	@Override
	public void close() {
		activeAnimations.close();
		listenerGraph.removeUpdateHooks();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
