package lighthouse.spaceinvaders;

import lighthouse.gameapi.Game;
import lighthouse.model.GameState;
import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.spaceinvaders.ui.SpaceInvadersSceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class SpaceInvadersGame implements Game {
    private final SpaceInvadersGameState gameState = new SpaceInvadersGameState();
    private final SpaceInvadersSceneLayer sceneLayer = new SpaceInvadersSceneLayer(gameState);
	private final DoubleVecBijection gridPosToPixels = new Scaling(2, 2);

    @Override
    public String getName() { return "Space Invaders"; }
    
    @Override
    public GameState getModel() { return gameState; }

	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }

	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridPosToPixels; }

    @Override
	public DoubleVecBijection getLighthouseToGridPos() { return DoubleVecBijection.IDENTITY; }
}
