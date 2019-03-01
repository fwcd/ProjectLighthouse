package lighthouse.ui.board.debug;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lighthouse.util.ListenerList;

public class AnimationTracker {
	private Map<String, Double> runningAnimations;
	private volatile boolean enabled = false;
	
	private final ListenerList<Void> changeListeners = new ListenerList<>("AnimationTracker.changeListeners");
	
	public synchronized void setRunningAnimationProgress(String name, double percentage) {
		if (enabled) {
			runningAnimations.put(name, percentage);
			changeListeners.fire();
		}
	}
	
	public synchronized void removeRunningAnimation(String name) {
		if (enabled) {
			runningAnimations.remove(name);
			changeListeners.fire();
		}
	}
	
	public synchronized double getRunningAnimationProgress(String name) {
		return enabled ? runningAnimations.get(name) : 0.0;
	}
	
	public synchronized Set<? extends String> getRunningAnimationNames() {
		return enabled ? runningAnimations.keySet() : Collections.emptySet();
	}
	
	public synchronized void setEnabled(boolean enabled) {
		if (enabled) {
			runningAnimations = new ConcurrentHashMap<>();
		}
		this.enabled = enabled;
	}
	
	public boolean isEnabled() { return enabled; }
	
	public ListenerList<Void> getChangeListeners() { return changeListeners; }
}
