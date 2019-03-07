package lighthouse.breakout;

import javax.swing.Timer;

import lighthouse.breakout.model.BreakoutGameState;
import lighthouse.breakout.ui.BreakoutController;
import lighthouse.breakout.ui.BreakoutSceneLayer;
import lighthouse.gameapi.Game;
import lighthouse.gameapi.GameInitializationContext;
import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.model.GameState;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;

public class BreakoutGame implements Game {
	private final BreakoutGameState gameState = new BreakoutGameState();
	private final BreakoutSceneLayer sceneLayer = new BreakoutSceneLayer(gameState);
	private final DoubleVecBijection gridToPixels = new Scaling(15, 30);
	private final DoubleVecBijection lighthouseToGridPos = DoubleVecBijection.IDENTITY;
	private final Timer timer;
	private final int maxFPS = 60;
	private SceneInteractionFacade sceneFacade;
	
	public BreakoutGame() {
		timer = new Timer(1000 / maxFPS, e -> {
			gameState.advance();
			sceneFacade.update();
		});
		timer.setRepeats(true);
	}
	
	@Override
	public String getName() { return "Breakout"; }
	
	@Override
	public SceneLayer getGameLayer() { return sceneLayer; }
	
	@Override
	public void initialize(GameInitializationContext context) {
		sceneFacade = context.getInteractionFacade();
	}
	
	@Override
	public void onOpen() {
		sceneFacade.setResponder(new BreakoutController(gameState));
		timer.start();
	}
	
	@Override
	public void onClose() {
		timer.stop();
	}
	
	@Override
	public GameState getModel() { return gameState; }
	
	@Override
	public boolean usesSimpleArrowKeys() { return true; }
	
	@Override
	public DoubleVecBijection getGridPosToPixels() { return gridToPixels; }
	
	@Override
	public DoubleVecBijection getLighthouseToGridPos() { return lighthouseToGridPos; }
}
