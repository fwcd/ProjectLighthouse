package lighthouse.spaceinvaders;

import javax.swing.Timer;

import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameInitializationContext;
import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.model.GameState;
import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.spaceinvaders.ui.SpaceInvadersController;
import lighthouse.spaceinvaders.ui.SpaceInvadersSceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class SpaceInvadersGame implements Game {
    private final SpaceInvadersGameState gameState = new SpaceInvadersGameState();
    private final SpaceInvadersSceneLayer sceneLayer = new SpaceInvadersSceneLayer(gameState);
    private final DoubleVecBijection gridPosToPixels = new Scaling(15, 15);
    private final Timer timer;
    private final int maxTPS = 60;
    
    private SceneInteractionFacade sceneFacade;
    
    public SpaceInvadersGame() {
        timer = new Timer(1000 / maxTPS, e -> {
            gameState.advance();
            sceneFacade.update();
        });
        timer.setRepeats(true);
    }
    
    @Override
    public void initialize(GameInitializationContext context) {
        sceneFacade = context.getInteractionFacade();
    }
    
    @Override
    public void onOpen() {
        timer.start();
        sceneFacade.setResponder(new SpaceInvadersController(gameState));
    }
    
    @Override
    public void onClose() {
        timer.stop();
    }

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
