package lighthouse.ui.debug.animations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.alee.laf.progressbar.WebProgressBar;

import lighthouse.ui.board.debug.AnimationTracker;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.Listener;

public class ActiveAnimationsView implements AutoCloseable {
	private final JPanel component;
	
	private final AnimationTracker tracker;
	private final Listener<Void> changeListener;
	
	private final Map<String, AnimationView> visibleAnimations = new HashMap<>();
	
	public ActiveAnimationsView(AnimationTracker tracker) {
		this.tracker = tracker;
		
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		changeListener = v -> SwingUtilities.invokeLater(this::update);
		tracker.getChangeListeners().add(changeListener);
	}
	
	private void update() {
		synchronized (tracker) {
			Set<String> remaining = new HashSet<>(visibleAnimations.keySet());
			
			for (String name : tracker.getRunningAnimationNames()) {
				if (visibleAnimations.containsKey(name)) {
					visibleAnimations.get(name).setProgress(tracker.getRunningAnimationProgress(name));
				} else {
					AnimationView animationView = new AnimationView(name);
					animationView.setProgress(0.0);
					component.add(animationView.getComponent());
					visibleAnimations.put(name, animationView);
				}
				remaining.remove(name);
			}
			
			for (String name : remaining) {
				component.remove(visibleAnimations.remove(name).getComponent());
			}
		}
		SwingUtilities.invokeLater(() -> {
			component.revalidate();
			component.repaint();
		});
	}
	
	private static class AnimationView {
		private JComponent component;
		private WebProgressBar progressBar;
		
		public AnimationView(String name) {
			progressBar = new WebProgressBar();
			component = LayoutUtils.vboxOf(
				new JLabel(name + ": "),
				progressBar
			);
		}
		
		public void setProgress(double value) { progressBar.setValue((int) (value * 100)); }
		
		public JComponent getComponent() { return component; }
	}
	
	@Override
	public void close() {
		tracker.getChangeListeners().remove(changeListener);
	}
	
	public JPanel getComponent() { return component; }
}
