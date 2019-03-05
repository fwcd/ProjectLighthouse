package lighthouse.gameapi;

import java.util.Collections;
import java.util.List;

import lighthouse.model.GameState;
import lighthouse.ui.AppContext;
import lighthouse.ui.EmptyViewController;
import lighthouse.ui.SwingViewController;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * A game module that can be run on the Lighthouse.
 */
public interface Game {
	String getName();
	
	GameState getModel();
	
	DoubleVecBijection getGridPosToPixels();
	
	DoubleVecBijection getLighthousePosToGridPos();
	
	default DoubleVecBijection getGridSizeToSize() { return getGridPosToPixels(); }
	
	default DoubleVecBijection getLighthouseSizeToGridSize() { return getLighthousePosToGridPos(); }
	
	default void initialize(AppContext context) {}
	
	default List<GameMenuEntry> getGameMenuEntries() { return Collections.emptyList(); }
	
	default SwingViewController getGameViewController() { return new EmptyViewController(); }
	
	default boolean hasCustomGameViewController() { return false; }
	
	default SwingViewController getSolverViewController() { return new EmptyViewController(); }
	
	default SwingViewController getControlsViewController() { return new EmptyViewController(); }
	
	default SwingViewController getStatisticsViewController() { return new EmptyViewController(); }
}
