package lighthouse.spaceinvaders.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Cannon implements BoxBounded {
    private static final int MAX_HP = 3;
    private final DoubleRect boundingBox;
    private final HealthPoints hp;
    
    public Cannon(DoubleRect boundingBox) {
        this(boundingBox, new HealthPoints(MAX_HP));
    }
    
    private Cannon(DoubleRect boundingBox, HealthPoints hp) {
        this.boundingBox = boundingBox;
        this.hp = hp;
    }
    
    public HealthPoints getHp() { return hp; }
    
    public Cannon damage() { return new Cannon(boundingBox, hp.decrease()); }
    
    public Cannon movedBy(int dx) { return new Cannon(boundingBox.movedBy(dx, 0), hp); }
    
    public Projectile shoot() { return new Projectile(boundingBox.getCenter(), new DoubleVec(0, -SpaceInvadersConstants.PROJECTILE_SPEED), true /* hitsAliens */); }
    
    @Override
    public DoubleRect getBoundingBox() { return boundingBox; }
}
