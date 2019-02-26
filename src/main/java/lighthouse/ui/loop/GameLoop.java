package lighthouse.ui.loop;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Game render/ticking loop.
 */
public class GameLoop {
	private static final Logger LOG = LoggerFactory.getLogger(GameLoop.class);
	
	private final List<Renderer> renderers = new ArrayList<>();
	private final List<Ticker> tickers = new ArrayList<>();
	
	private int maxFPS = 60; // The upper fps limit
	private int maxTPS = 20; // The upper tps limit
	private int fps = 0; // The actual frames per second
	private int tps = 0; // The actual ticks per second
	private Timer timer;
	private boolean running = false;
	
	private long lastTick = 0;
	private long lastSecond = 0;
	private int ticks = 0;
	private int frames = 0;
	
	/** Starts the game loop asynchronously on another thread. */
	public void start() {
		stop(); // Stop any previous timers
		int frameDelay = 1000 / maxFPS; // The delay between each frame in milliseconds
		int tickDelay = 1000 / maxTPS; // The delay between each tick in milliseconds
		
		timer = new Timer(frameDelay, l -> {
			// Render a frame
			gameFrame();
			frames++;
			
			long now = System.currentTimeMillis();
			
			// Tick if 'tpsDelay' milliseconds have passed
			// Note that this timer requires the tps to be
			// lower than the fps.
			if ((now - lastTick) > tickDelay) {
				gameTick();
				ticks++;
			}
			
			// Calculate fps and tps if a second has passed
			if ((now - lastSecond) > 1000) {
				fps = frames;
				tps = ticks;
				ticks = 0;
				frames = 0;
				lastSecond = now;
				LOG.debug("{} fps, {} tps", fps, tps);
			}
		});
		timer.start();
		running = true;
	}
	
	/** Performs a single tick. */
	private void gameTick() {
		for (Ticker ticker : tickers) {
			ticker.tick();
		}
	}
	
	/** Draws a single frame. */
	private void gameFrame() {
		for (Renderer renderer : renderers) {
			renderer.render();
		}
	}
	
	/** Stops the game loop. */
	public void stop() {
		if (timer != null) {
			timer.stop();
		}
		running = false;
	}
	
	/** Fetches the current frames per second. */
	public int getFPS() {
		return fps;
	}
	
	/** Fetches the current ticks per second. */
	public int getTPS() {
		return tps;
	}
	
	/** Fetches the fps limit. */
	public int getMaxFPS() {
		return maxFPS;
	}
	
	/** Fetches the tps limit. */
	public int getMaxTPS() {
		return maxTPS;
	}
	
	/** Adjusts the fps limit.  */
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
		if (running) {
			start(); // Restart the loop with the new delay
		}
	}
	
	/** Adjusts the tps limit.  */
	public void setMaxTPS(int maxTPS) {
		this.maxTPS = maxTPS;
		if (running) {
			start(); // Restart the loop with the new delay
		}
	}
	
	public void addRenderer(Renderer renderer) { renderers.add(renderer); }
	
	public void addTicker(Ticker ticker) { tickers.add(ticker); }
	
	public void removeRenderer(Renderer renderer) { renderers.remove(renderer); }
	
	public void removeTicker(Ticker ticker) { tickers.remove(ticker); }
}
