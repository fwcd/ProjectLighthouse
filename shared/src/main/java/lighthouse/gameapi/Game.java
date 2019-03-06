package lighthouse.gameapi;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import lighthouse.model.GameState;
import lighthouse.ui.EmptyViewController;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * A game module that can be run on the Lighthouse.
 */
public interface Game {
	String getName();
	
	GameState getModel();
	
	SceneLayer getGameLayer();
	
	DoubleVecBijection getGridPosToPixels();
	
	DoubleVecBijection getLighthouseToGridPos();
	
	default void onOpen() {}
	
	default void onClose() {}
	
	default boolean hasSimpleArrowKeys() { return false; }
	
	default DoubleVecBijection getGridSizeToPixels() { return getGridPosToPixels(); }
	
	default DoubleVecBijection getLighthouseToGridSize() { return getLighthouseToGridPos(); }
	
	default void initialize(GameInitializationContext context) {}
	
	default List<GameMenuEntry> getGameMenuEntries() { return Collections.emptyList(); }
	
	default boolean hasCustomGameViewController() { return false; }
	
	default CustomGameViewController getCustomGameViewController() { throw new NoSuchElementException("No custom game view controller present"); }
	
	default SwingViewController getSolverViewController() { return new EmptyViewController(); }
	
	default SwingViewController getControlsViewController() { return new EmptyViewController(); }
	
	default SwingViewController getStatisticsViewController() { return new EmptyViewController(); }
}
