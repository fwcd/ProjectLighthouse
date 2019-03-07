package lighthouse.breakout;

import lighthouse.breakout.model.BreakoutGameState;
import lighthouse.breakout.ui.BreakoutSceneLayer;
import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class BreakoutGame implements Game {
	private final BreakoutGameState gameState = new BreakoutGameState();
	private final BreakoutSceneLayer sceneLayer = new BreakoutSceneLayer(gameState);
	private final DoubleVecBijection gridToPixels = new Scaling(30, 15);
	private final DoubleVecBijection lighthouseToGridPos = DoubleVecBijection.IDENTITY;
	
	@Override
	public String getName() { return "Breakout"; }
	
	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }
	
	@Override
	public GameState getModel() { return gameState; }
	
	@Override
	public boolean usesSimpleArrowKeys() { return true; }
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return lighthouseToGridPos; }
}
