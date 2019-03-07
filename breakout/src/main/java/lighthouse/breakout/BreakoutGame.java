package lighthouse.breakout;

import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOG = LoggerFactory.getLogger(BreakoutGame.class);
	private final BreakoutGameState gameState = new BreakoutGameState();
	private final BreakoutSceneLayer sceneLayer = new BreakoutSceneLayer(gameState);
	private final DoubleVecBijection gridToPixels = new Scaling(15, 15);
	private final DoubleVecBijection lighthouseToGridPos = DoubleVecBijection.IDENTITY;
	private final Timer timer;
	private final int maxTPS = 60;
	private SceneInteractionFacade sceneFacade;
	
	public BreakoutGame() {
		timer = new Timer(1000 / maxTPS, e -> {
			LOG.debug("Running Breakout tick...");
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
		LOG.debug("Starting Breakout timer");
		timer.start();
	}
	
	@Override
	public void onClose() {
		LOG.debug("Stopping Breakout timer");
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
