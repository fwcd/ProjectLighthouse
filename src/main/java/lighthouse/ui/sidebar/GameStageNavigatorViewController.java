package lighthouse.ui.sidebar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.model.Game;
import lighthouse.model.GameStage;
import lighthouse.model.GameStages;

/**
 * Manages the level navigation that allows the
 * user to step through different stages of a level.
 */
public class GameStageNavigatorViewController {
	private final WebStepProgress component;
	private final Game game;
	private GameStage selectedStage;
	
	public GameStageNavigatorViewController(Game game) {
		this.game = game;
		component = new WebStepProgress();
		component.addSteps(GameStages.STAGES.stream().sorted().map(GameStage::getName).toArray(String[]::new));
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { onChange(); }
			
			@Override
			public void mouseDragged(MouseEvent e) { onChange(); }
			
			private void onChange() {
				int newIndex = component.getSelectedStepIndex();
				if (newIndex != selectedStage.getIndex()) {
					select(GameStages.STAGES.get(newIndex));
				}
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
		
		select(GameStages.CURRENT);
	}
	
	private void select(GameStage newStage) {
		if (selectedStage != null && newStage.getIndex() != selectedStage.getIndex()) {
			newStage.transitionFrom(selectedStage, game);
		}
		selectedStage = newStage;
		component.setSelectedStepIndex(newStage.getIndex());
	}
	
	public JComponent getComponent() { return component; }
}
