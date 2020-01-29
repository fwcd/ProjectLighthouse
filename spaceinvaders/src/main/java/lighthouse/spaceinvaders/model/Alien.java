package lighthouse.spaceinvaders.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Alien implements BoxBounded {
    private final DoubleRect boundingBox;
    
    public Alien(DoubleVec position) {
        boundingBox = new DoubleRect(position, 1.0, 1.0);
    }
    
    private Alien(DoubleRect boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    @Override
    public DoubleRect getBoundingBox() { return boundingBox; }

    public Alien movedBy(DoubleVec delta) { return new Alien(boundingBox.movedBy(delta)); }
    
    public Projectile shoot() { return new Projectile(boundingBox.getTopLeft(), new DoubleVec(0, SpaceInvadersConstants.PROJECTILE_SPEED), false /* hitsAliens */); }
}
