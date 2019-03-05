package lighthouse.ui.scene;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.ui.scene.controller.NoResponder;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.input.SceneKeyInput;
import lighthouse.ui.scene.input.SceneMouseInput;
import lighthouse.ui.scene.view.LighthouseView;
import lighthouse.ui.scene.view.LocalSceneView;
import lighthouse.ui.scene.view.SceneView;
import lighthouse.ui.scene.viewmodel.LighthouseViewModel;
import lighthouse.ui.scene.viewmodel.graphics.SceneViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * Manages a scene together with its views.
 */
public class SceneViewController implements SwingViewController {
	private static final Logger LOG = LoggerFactory.getLogger(SceneViewController.class);
	private final JComponent component;
	private final AnimationRunner animationRunner = new SceneAnimationRunner();
	
	private final SceneViewModel viewModel;
	private LighthouseViewModel lighthouseViewModel;
	private SceneMouseInput mouseInput;
	
	private final LocalSceneView localView;
	private final List<SceneView> sceneViews = new ArrayList<>();
	private final List<LighthouseView> lighthouseViews = new ArrayList<>();
	private final DelegateResponder responder = new DelegateResponder(NoResponder.INSTANCE);
	
	public SceneViewController() {
		viewModel = new SceneViewModel();
		component = new JPanel(new BorderLayout());
		
		localView = new LocalSceneView(DoubleVecBijection.IDENTITY.floor(), DoubleVecBijection.IDENTITY.floor());
		sceneViews.add(localView);
		component.add(localView.getComponent(), BorderLayout.CENTER);
		
		SceneKeyInput keyInput = new SceneKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);
	}
	
	public void render() {
		for (SceneView view : sceneViews) {
			view.draw(viewModel);
		}
		if (lighthouseViewModel == null) {
			LOG.warn("Could not render scene to lighthouse views without transformation functions");
		} else {
			for (LighthouseView view : lighthouseViews) {
				view.draw(lighthouseViewModel);
			}
		}
	}
	
	public void relayout(IntVec gridSize) {
		localView.relayout(gridSize);
	}
	
	public void setGridTransforms(DoubleVecBijection gridPosToPixels, DoubleVecBijection gridSizeToPixels) {
		if (mouseInput != null) {
			localView.removeMouseInput(mouseInput);
		}
		mouseInput = new SceneMouseInput(gridPosToPixels);
		localView.addMouseInput(mouseInput);
	}
	
	public void setLighthouseTransforms(DoubleVecBijection lighthouseToGridSize, DoubleVecBijection lighthouseToGridPos) {
		lighthouseViewModel = new LighthouseViewModel(viewModel, lighthouseToGridSize, lighthouseToGridPos);
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
	
	public LocalSceneView getLocalView() { return localView; }
	
	public SceneViewModel getViewModel() { return viewModel; }
	
	@Override
	public JComponent getComponent() { return component; }
}
