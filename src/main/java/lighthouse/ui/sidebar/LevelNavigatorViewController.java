package lighthouse.ui.sidebar;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.model.Game;
import lighthouse.model.GameStage;
import lighthouse.model.GameStages;
import lighthouse.ui.ViewController;
import lighthouse.ui.loop.GameLoop;

/**
 * Manages the level navigation that allows the
 * user to step through different stages of a level.
 */
public class LevelNavigatorViewController implements ViewController {
	private final WebStepProgress component;
	private final Game game;
	private GameStage selectedStage;
	
	public LevelNavigatorViewController(Game game, GameLoop loop) {
		this.game = game;
		component = new WebStepProgress();
		component.addSteps(GameStages.STAGES.stream().sorted()
			.map(stage -> new GameStageIconViewController(stage, game, loop))
			.map(ViewController::getComponent)
			.toArray(Component[]::new));
		
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
		game.getLevelListeners().add(level -> {
			// Update stage if the level has changed
			selectedStage.transitionFrom(selectedStage, game);
		});
	}
	
	private void select(GameStage newStage) {
		if (selectedStage != null && newStage.getIndex() != selectedStage.getIndex()) {
			newStage.transitionFrom(selectedStage, game);
		}
		selectedStage = newStage;
		component.setSelectedStepIndex(newStage.getIndex());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
