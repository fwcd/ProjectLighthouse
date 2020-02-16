package lighthouse.spaceinvaders.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lighthouse.spaceinvaders.model.Alien;
import lighthouse.spaceinvaders.model.Cannon;
import lighthouse.spaceinvaders.model.Projectile;
import lighthouse.spaceinvaders.model.Shield;
import lighthouse.spaceinvaders.model.SpaceInvadersGameState;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneRect;
import lighthouse.ui.scene.viewmodel.graphics.SceneShape;
import lighthouse.ui.scene.viewmodel.graphics.Shading;
import lighthouse.util.ColorUtils;

public class SpaceInvadersSceneLayer implements SceneLayer {
    private static final Color WIN_COLOR = new Color(0x0f8200); // dark green
    private static final Color LOSE_COLOR = new Color(0x700013); // dark red
    private final SpaceInvadersGameState gameState;
    
    public SpaceInvadersSceneLayer(SpaceInvadersGameState gameState) {
        this.gameState = gameState;
    }
    
    @Override
    public boolean hasBackground() { return gameState.isGameOver(); }
    
    @Override
    public Color getBackground() { return gameState.isGameWon() ? WIN_COLOR : LOSE_COLOR; }

    @Override
    public List<SceneShape> getShapes() {
        List<SceneShape> shapes = new ArrayList<>();
        
        for (Alien alien : gameState.getSwarm()) {
            shapes.add(new SceneRect(alien.getBoundingBox(), Color.GREEN, Shading.FILLED));
        }
        
        for (Shield shield : gameState.getShields()) {
            Color baseColor = Color.GREEN;
            Color color = ColorUtils.withAlphaPercent(shield.getHp().getPercent(), baseColor);
            shapes.add(new SceneRect(shield.getBoundingBox(), color, Shading.FILLED));
        }
        
        for (Projectile projectile : gameState.getFlyingProjectiles()) {
            shapes.add(new SceneRect(projectile.getBoundingBox(), Color.WHITE, Shading.FILLED));
        }
        
        Cannon cannon = gameState.getCannon();
        shapes.add(new SceneRect(cannon.getBoundingBox(), ColorUtils.withAlphaPercent(cannon.getHp().getPercent(), Color.WHITE), Shading.FILLED));
        return shapes;
    }
}
