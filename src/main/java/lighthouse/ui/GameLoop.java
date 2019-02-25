package lighthouse.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.ui.board.view.BoardView;

/**
 * The Game rendering loop.
 */
public class GameLoop {
	private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);
	private final List<BoardView> views;
	private final Board model;
	
	private int maxFPS = 60; // The upper fps limit
	private int fps = 0; // The actual fps
	private Timer timer;
	private boolean running = false;
	
	/** Creates a new game loop which renders to the given views. */
	public GameLoop(List<BoardView> views, Board model) {
		this.views = views;
		this.model = model;
	}
	
	/** Starts the game loop asynchronously on another thread. */
	public void start() {
		stop(); // Stop any previous timers
		int delayMs = 1000 / maxFPS; // The delay between each frame in milliseconds
		
		timer = new Timer("Game loop");
		timer.scheduleAtFixedRate(new TimerTask() {
			private long lastSecond = 0;
			private int frames = 0;
			
			@Override
			public void run() {
				// Render a frame
				gameFrame();
				frames++;
				
				// Calculate fps if a second has passed
				long now = System.currentTimeMillis();
				if ((now - lastSecond) > 1000) {
					fps = frames;
					frames = 0;
					lastSecond = now;
					LOG.debug("{} fps", fps);
				}
			}
		}, delayMs, delayMs);
		running = true;
	}
	
	/** Draws a single frame. */
	private void gameFrame() {
		for (BoardView view : views) {
			view.draw(model);
		}
	}
	
	/** Stops the game loop. */
	public void stop() {
		if (timer != null) {
			timer.cancel();
		}
		running = false;
	}
	
	/** Fetches the current fps. */
	public int getFPS() {
		return fps;
	}
	
	/** Fetches the fps limit. */
	public int getMaxFPS() {
		return maxFPS;
	}
	
	/** Adjusts the fps limit.  */
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
		if (running) {
			start(); // Restart the loop with the new delay
		}
	}
}
