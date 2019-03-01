package lighthouse.ui.board.debug;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AnimationTracker {
	private Map<String, Double> runningAnimations;
	private boolean enabled = false;
	
	public void setRunningAnimationProgress(String name, double percentage) {
		if (enabled) {
			runningAnimations.put(name, percentage);
		}
	}
	
	public void removeRunningAnimation(String name) {
		if (enabled) {
			runningAnimations.remove(name);
		}
	}
	
	public double getRunningAnimationProgress(String name) {
		return enabled ? runningAnimations.get(name) : 0.0;
	}
	
	public Set<? extends String> getRunningAnimationNames() {
		return enabled ? runningAnimations.keySet() : Collections.emptySet();
	}
	
	public void setEnabled(boolean enabled) {
		if (enabled) {
			runningAnimations = new ConcurrentHashMap<>();
		}
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
