package lighthouse.ui.scene;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.view.SceneView;

public class SceneViewController implements SwingViewController {
	private final JComponent component;
	private final List<SceneView> views = new ArrayList<>();
	private final AnimationRunner animationRunner = new SceneAnimationRunner();
	
	public SceneViewController() {
		component = new JPanel();
	}
	
	public void addView(SceneView view) {
		views.add(view);
	}
	
	public AnimationRunner getAnimationRunner() { return animationRunner; }
	
	@Override
	public JComponent getComponent() { return component; }
}
