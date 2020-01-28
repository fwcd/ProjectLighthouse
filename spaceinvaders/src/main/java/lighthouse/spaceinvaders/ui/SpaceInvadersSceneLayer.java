package lighthouse.spaceinvaders.ui;

import java.util.Collections;
import java.util.List;

import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneShape;

public class SpaceInvadersSceneLayer implements SceneLayer {
    private final SpaceInvadersGameState gameState;
    
    public SpaceInvadersSceneLayer(SpaceInvadersGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public List<SceneShape> getShapes() {
        return Collections.emptyList(); // TODO
    }
}
