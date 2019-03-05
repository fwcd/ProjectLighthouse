package lighthouse.puzzle.ui.board;

import javax.swing.Timer;

import lighthouse.puzzle.ui.board.debug.AnimationTracker;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.scene.viewmodel.graphics.Animation;
import lighthouse.ui.scene.viewmodel.graphics.AnimationPlayer;
import lighthouse.util.Updatable;

public class BoardAnimationRunner {
	private final BoardViewModel viewModel;
	private final AnimationTracker tracker = new AnimationTracker();
	private final int animationFPS;
	private final Updatable gameUpdater;
	
	public BoardAnimationRunner(BoardViewModel viewModel, int animationFPS, Updatable gameUpdater) {
		this.viewModel = viewModel;
		this.animationFPS = animationFPS;
		this.gameUpdater = gameUpdater;
	}
	
	public void play(Animation animation) {
		String name = animation.getName() + " #" + Integer.toHexString(animation.hashCode());
		int totalFrames = animation.getTotalFrames();
		AnimationPlayer player = new AnimationPlayer(animation);
		
		viewModel.addOverlay(player);
		tracker.setRunningAnimationProgress(name, 0.0);
		
		Timer timer = new Timer(1000 / animationFPS, e -> {
			if (player.hasNextFrame()) {
				player.nextFrame();
				gameUpdater.update();
				
				if (tracker.isEnabled()) {
					tracker.setRunningAnimationProgress(name, player.getCurrentFrame() / (double) totalFrames);
				}
			} else {
				tracker.removeRunningAnimation(name);
				viewModel.removeOverlay(player);
				gameUpdater.update();
				
				((Timer) e.getSource()).stop();
			}
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	
	public AnimationTracker getTracker() { return tracker; }
}
