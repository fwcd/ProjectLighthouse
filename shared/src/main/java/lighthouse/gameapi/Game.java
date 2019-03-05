package lighthouse.gameapi;

import lighthouse.model.GameState;
import lighthouse.ui.AppContext;
import lighthouse.ui.EmptyViewController;
import lighthouse.ui.ViewController;

/**
 * A game module that can be run on the Lighthouse.
 */
public interface Game {
	String getName();
	
	GameState getModel();
	
	default void initialize(AppContext context) {}
	
	default ViewController getGameViewController() { return new EmptyViewController(); }
	
	default ViewController getSolverViewController() { return new EmptyViewController(); }
	
	default ViewController getControlsViewController() { return new EmptyViewController(); }
	
	default ViewController getStatisticsViewController() { return new EmptyViewController(); }
}
