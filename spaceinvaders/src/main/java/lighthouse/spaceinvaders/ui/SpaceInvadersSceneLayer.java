package lighthouse.spaceinvaders.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lighthouse.spaceinvaders.model.Alien;
import lighthouse.spaceinvaders.model.Projectile;
import lighthouse.spaceinvaders.model.Shield;
import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneRect;
import lighthouse.ui.scene.viewmodel.graphics.SceneShape;
import lighthouse.ui.scene.viewmodel.graphics.Shading;
import lighthouse.util.DoubleRect;

public class SpaceInvadersSceneLayer implements SceneLayer {
    private final SpaceInvadersGameState gameState;
    
    public SpaceInvadersSceneLayer(SpaceInvadersGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public List<SceneShape> getShapes() {
        List<SceneShape> shapes = new ArrayList<>();
        
        for (Alien alien : gameState.getSwarm()) {
            shapes.add(new SceneRect(alien.getBoundingBox(), Color.GREEN, Shading.FILLED));
        }
        
        for (Shield shield : gameState.getShields()) {
            shapes.add(new SceneRect(shield.getBoundingBox(), Color.GREEN, Shading.FILLED));
        }
        
        for (Projectile projectile : gameState.getFlyingProjectiles()) {
            shapes.add(new SceneRect(projectile.getBoundingBox(), Color.WHITE, Shading.FILLED));
        }
        
        shapes.add(new SceneRect(gameState.getCannon().getBoundingBox(), Color.WHITE, Shading.FILLED));
        return shapes;
    }
}
