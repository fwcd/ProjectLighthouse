package lighthouse.spaceinvaders.model;

import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Cannon {
    private final DoubleRect boundingBox;
    
    public Cannon(DoubleRect boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public Cannon movedBy(int dx) { return new Cannon(boundingBox.movedBy(dx, 0)); }
    
    public boolean hitBy(Projectile projectile) { return !projectile.doesHitAliens() && boundingBox.contains(projectile.getPosition()); }
    
    public Projectile shoot() { return new Projectile(boundingBox.getCenter(), new DoubleVec(0, -SpaceInvadersConstants.PROJECTILE_SPEED), true /* hitsAliens */); }
    
    public DoubleRect getBoundingBox() { return boundingBox; }
}
