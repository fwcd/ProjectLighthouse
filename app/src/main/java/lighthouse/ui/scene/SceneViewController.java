package lighthouse.ui.scene;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.alee.extended.panel.CenterPanel;

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
import lighthouse.ui.scene.viewmodel.graphics.Animation;
import lighthouse.ui.scene.viewmodel.graphics.AnimationPlayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * Manages a scene together with its views.
 */
public class SceneViewController implements SwingViewController, AnimationRunner {
	private static final Logger LOG = LoggerFactory.getLogger(SceneViewController.class);
	private final JComponent component;
	
	private final int maxFPS = 60;
	private final SceneViewModel viewModel;
	private LighthouseViewModel lighthouseViewModel;
	private SceneMouseInput mouseInput;
	
	private final LocalSceneView localView;
	private final List<SceneView> sceneViews = new ArrayList<>();
	private final List<LighthouseView> lighthouseViews = new ArrayList<>();
	private final DelegateResponder responder = new DelegateResponder(NoResponder.INSTANCE);
	
	private boolean hasRunningTransitionTimer = false;
	
	public SceneViewController() {
		viewModel = new SceneViewModel();
		component = new JPanel(new BorderLayout());
		
		localView = new LocalSceneView(DoubleVecBijection.IDENTITY.floor(), DoubleVecBijection.IDENTITY.floor());
		sceneViews.add(localView);
		component.add(new CenterPanel(localView.getComponent()), BorderLayout.CENTER);
		
		SceneKeyInput keyInput = new SceneKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);
	}
	
	/** Renders the scene to the output views. */
	public void render() {
		updateTransitionTimer();
		
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
		
		SwingUtilities.invokeLater(component::repaint);
	}
	
	/** Ensure that there is a timer handling running layer transitions. */
	private void updateTransitionTimer() {
		LOG.debug("Updating the scene's transition timer...");
		
		if (!hasRunningTransitionTimer) {
			hasRunningTransitionTimer = true;
			startRepeatingTimer(e -> {
				boolean updated = false;
				
				for (SceneLayer layer : viewModel) {
					if (layer.hasNextTransitionFrame()) {
						layer.nextTransitionFrame();
						updated = true;
						render();
					}
				}
				
				if (!updated) {
					hasRunningTransitionTimer = false;
					((Timer) e.getSource()).stop();
				}
			});
		}
	}
	
	@Override
	public void play(Animation animation) {
		AnimationPlayer player = new AnimationPlayer(animation);
		viewModel.addLayer(player);
		
		startRepeatingTimer(e -> {
			if (player.hasNextFrame()) {
				player.nextFrame();
			} else {
				viewModel.removeLayer(player);
				((Timer) e.getSource()).stop();
			}
		});
	}
	
	private void startRepeatingTimer(ActionListener action) {
		Timer timer = new Timer(1000 / maxFPS, action);
		timer.setRepeats(true);
		timer.start();
	}
	
	public void relayout(IntVec gridSize) {
		localView.relayout(gridSize);
	}
	
	public void setGridTransforms(DoubleVecBijection gridPosToPixels, DoubleVecBijection gridSizeToPixels) {
		if (mouseInput != null) {
			localView.removeMouseInput(mouseInput);
		}
		mouseInput = new SceneMouseInput(gridPosToPixels);
		mouseInput.addResponder(responder);
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
	
	public LocalSceneView getLocalView() { return localView; }
	
	public SceneViewModel getViewModel() { return viewModel; }
	
	@Override
	public JComponent getComponent() { return component; }
}
