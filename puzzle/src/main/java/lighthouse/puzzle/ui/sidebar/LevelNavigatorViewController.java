package lighthouse.puzzle.ui.sidebar;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.alee.extended.progress.WebStepProgress;

import lighthouse.puzzle.ui.PuzzleGameViewController;
import lighthouse.puzzle.ui.perspectives.CommonPerspective;
import lighthouse.puzzle.ui.perspectives.InGamePerspective;
import lighthouse.ui.SwingViewController;

/**
 * Manages the level navigation that allows the
 * user to step through different stages of a level.
 */
public class LevelNavigatorViewController implements SwingViewController {
	private final WebStepProgress component;
	
	public LevelNavigatorViewController(PuzzleGameViewController game) {
		component = new WebStepProgress();
		component.addSteps(CommonPerspective.PERSPECTIVES.stream()
			.sorted()
			.map(perspective -> new PerspectiveIconViewController(perspective, game))
			.map(SwingViewController::getComponent)
			.toArray(Component[]::new));
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { onChange(); }
			
			@Override
			public void mouseDragged(MouseEvent e) { onChange(); }
			
			private void onChange() {
				int newIndex = component.getSelectedStepIndex();
				if (newIndex != game.getPerspective().getIndex()) {
					game.show(CommonPerspective.byIndex(newIndex));
				}
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
		
		game.show(InGamePerspective.INSTANCE);
		game.getPerspectiveListeners().add(perspective -> {
			component.setSelectedStepIndex(perspective.getIndex());
		});
		component.setSelectedStepIndex(game.getPerspective().getIndex());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
