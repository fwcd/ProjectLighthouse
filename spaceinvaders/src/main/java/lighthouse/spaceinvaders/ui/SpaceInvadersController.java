package lighthouse.spaceinvaders.ui;

import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.IntVec;

public class SpaceInvadersController implements SceneResponder {
    private final SpaceInvadersGameState gameState;
    
    public SpaceInvadersController(SpaceInvadersGameState gameState) {
        this.gameState = gameState;
    }
    
    @Override
    public IntVec right(IntVec gridPos) {
        gameState.moveCannon(1);
        return gridPos;
    }
    
    @Override
    public IntVec left(IntVec gridPos) {
        gameState.moveCannon(-1);
        return gridPos;
    }
    
    @Override
    public boolean fire() {
        gameState.fireCannon();
        return true;
    }
}
