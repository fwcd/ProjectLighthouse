package lighthouse.ui.scene;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.ui.scene.controller.NoResponder;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.view.LighthouseView;
import lighthouse.ui.scene.view.SceneView;

/**
 * Manages a scene together with its views.
 */
public class SceneViewController implements SwingViewController {
	private final JComponent component;
	private final AnimationRunner animationRunner = new SceneAnimationRunner();
	
	private final List<SceneView> sceneViews = new ArrayList<>();
	private final List<LighthouseView> lighthouseViews = new ArrayList<>();
	private final DelegateResponder responder = new DelegateResponder(NoResponder.INSTANCE);
	
	public SceneViewController() {
		component = new JPanel();
	}
	
	public void render() {
		// TODO
	}
	
	public void addSceneView(SceneView view) {
		sceneViews.add(view);
	}
	
	public void addLighthouseView(LighthouseView view) {
		lighthouseViews.add(view);
	}
	
	public void setResponder(SceneResponder responder) { this.responder.setDelegate(responder); }
	
	public DelegateResponder getResponder() { return responder; }
	
	public AnimationRunner getAnimationRunner() { return animationRunner; }
	
	@Override
	public JComponent getComponent() { return component; }
}
