package lighthouse.ui.sidebar;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.loop.GameLoop;
import lighthouse.ui.stage.GameStages;

/**
 * Manages the level navigation that allows the
 * user to step through different stages of a level.
 */
public class LevelNavigatorViewController implements ViewController {
	private final WebStepProgress component;
	
	public LevelNavigatorViewController(GameViewController game, GameLoop loop) {
		component = new WebStepProgress();
		component.addSteps(GameStages.STAGES.stream().sorted()
			.map(stage -> new GameStageIconViewController(stage, game.getModel(), loop))
			.map(ViewController::getComponent)
			.toArray(Component[]::new));
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { onChange(); }
			
			@Override
			public void mouseDragged(MouseEvent e) { onChange(); }
			
			private void onChange() {
				int newIndex = component.getSelectedStepIndex();
				if (newIndex != game.getStage().getIndex()) {
					game.switchToStage(GameStages.STAGES.get(newIndex));
				}
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
		
		game.switchToStage(GameStages.CURRENT);
		game.getStageListeners().add(stage -> {
			component.setSelectedStepIndex(stage.getIndex());
		});
		component.setSelectedStepIndex(game.getStage().getIndex());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
