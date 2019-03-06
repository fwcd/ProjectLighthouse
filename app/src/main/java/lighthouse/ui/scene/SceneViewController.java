package lighthouse.ui.scene;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.alee.extended.panel.CenterPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.RenderListener;
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
	private SceneKeyInput keyInput;
	
	private final LocalSceneView localView;
	private final List<SceneView> sceneViews = new ArrayList<>();
	private final List<LighthouseView> lighthouseViews = new ArrayList<>();
	private final DelegateResponder responder = new DelegateResponder(NoResponder.INSTANCE);
	private final Set<AnimationPlayer> runningAnimations = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final List<RenderListener> renderListeners = new ArrayList<>();
	
	private boolean hasRunningRenderTimer = false;
	
	public SceneViewController() {
		viewModel = new SceneViewModel();
		component = new JPanel(new BorderLayout());
		
		localView = new LocalSceneView(DoubleVecBijection.IDENTITY.floor(), DoubleVecBijection.IDENTITY.floor());
		sceneViews.add(localView);
		component.add(new CenterPanel(localView.getComponent()), BorderLayout.CENTER);
		
		keyInput = new SceneKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);
	}
	
	/** Renders the scene to the output views. */
	public void render() {
		updateRenderTimer();
		
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
		
		for (RenderListener listener : renderListeners) {
			listener.onRender();
		}
		
		SwingUtilities.invokeLater(component::repaint);
	}
	
	/** Ensure that there is a timer handling running animations and layer transitions. */
	private void updateRenderTimer() {
		LOG.debug("Updating the scene's transition timer...");
		
		if (!hasRunningRenderTimer) {
			hasRunningRenderTimer = true;
			Set<SceneLayer> removedLayers = new HashSet<>();
			
			LOG.debug("Starting new render timer...");
			startRepeatingTimer(e -> {
				removedLayers.clear();
				boolean updated = false;
				
				for (SceneLayer layer : viewModel) {
					if (layer.hasNextTransitionFrame()) {
						layer.nextTransitionFrame();
						updated = true;
					}
				}
				
				if (!runningAnimations.isEmpty()) {
					// Advance all animations
					for (Iterator<AnimationPlayer> iterator = runningAnimations.iterator(); iterator.hasNext();) {
						AnimationPlayer player = iterator.next();
						if (player.hasNextFrame()) {
							player.nextFrame();
						} else {
							viewModel.removeLayer(player);
							iterator.remove();
						}
					}
					
					updated = true;
				}
				
				if (updated) {
					render();
				} else {
					LOG.debug("Stopping render timer");
					hasRunningRenderTimer = false;
					((Timer) e.getSource()).stop();
				}
			});
		}
	}
	
	@Override
	public void play(Animation animation) {
		AnimationPlayer player = new AnimationPlayer(animation);
		viewModel.addLayer(player);
		runningAnimations.add(player);
		updateRenderTimer();
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
	
	public void addRenderListener(RenderListener listener) {
		renderListeners.add(listener);
	}
	
	public void removeRenderListener(RenderListener listener) {
		renderListeners.remove(listener);
	}
	
	public SceneMouseInput getMouseInput() { return mouseInput; }
	
	public SceneKeyInput getKeyInput() { return keyInput; }
	
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
