package lighthouse.spaceinvaders.model;

import lighthouse.util.IntRect;
import lighthouse.util.IntVec;

public class Cannon {
    private final IntRect boundingBox;
    
    public Cannon(IntRect boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public Cannon movedBy(int dx) { return new Cannon(boundingBox.movedBy(dx, 0)); }
    
    public boolean hitBy(Projectile projectile) { return !projectile.doesHitAliens() && boundingBox.contains(projectile.getPosition()); }
    
    public Projectile shoot() { return new Projectile(boundingBox.getCenter(), new IntVec(0, -1), true /* hitsAliens */); }
    
    public IntRect getBoundingBox() { return boundingBox; }
}
