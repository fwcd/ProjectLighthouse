package lighthouse.ui.board.overlay;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.grid.WritableColorGrid;

/**
 * A facility that plays animations on a high-
 * resolution and a low-resolution output at
 * a user-defined framerate.
 */
public class AnimationPlayer {
	private static final Logger LOG = LoggerFactory.getLogger(AnimationPlayer.class);
	private final List<AnimationState> states = new ArrayList<>();
	private final Graphics2D g2d;
	private final WritableColorGrid grid;
	private Timer timer;
	
	public AnimationPlayer(Graphics2D g2d, WritableColorGrid grid) {
		this.g2d = g2d;
		this.grid = grid;
	}
	
	public void play(Animation animation) {
		if (timer == null) {
			LOG.warn("Called AnimationPlayer.play before starting the player.");
		}
		states.add(new AnimationState(animation));
	}
	
	private void renderFrame() {
		Iterator<AnimationState> iterator = states.iterator();
		while (iterator.hasNext()) {
			AnimationState state = iterator.next();
			if (state.hasFrame()) {
				state.drawHighRes(g2d);
				state.drawLowRes(grid);
				state.advance();
			} else {
				// Remove animation that have finished playing
				iterator.remove();
			}
		}
	}
	
	public void start(int maxFPS) {
		if (timer != null) {
			timer.stop();
		}
		timer = new Timer(maxFPS, l -> renderFrame());
		timer.start();
	}
}
