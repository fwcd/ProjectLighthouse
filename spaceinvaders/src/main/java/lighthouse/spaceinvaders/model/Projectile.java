package lighthouse.spaceinvaders.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;

public class Projectile implements BoxBounded {
    private final DoubleRect boundingBox;
    private final DoubleVec velocity;
    private boolean hitsAliens;
    
    public Projectile(DoubleVec position, DoubleVec velocity, boolean hitsAliens) {
        DoubleVec radiusVec = new DoubleVec(0.2, 0.2);
        boundingBox = new DoubleRect(position.sub(radiusVec), radiusVec.scale(2));
        this.velocity = velocity;
        this.hitsAliens = hitsAliens;
    }
    
    private Projectile(DoubleRect boundingBox, DoubleVec velocity, boolean hitsAliens) {
        this.boundingBox = boundingBox;
        this.velocity = velocity;
        this.hitsAliens = hitsAliens;
    }
    
    @Override
    public DoubleRect getBoundingBox() { return boundingBox; }

    public DoubleVec getVelocity() { return velocity; }
    
    public boolean doesHitAliens() { return hitsAliens; }
    
    public Projectile fly() { return new Projectile(boundingBox.movedBy(velocity), velocity, hitsAliens); }
    
    public boolean isOutOfBounds(int boardWidth, int boardHeight) {
        return boundingBox.getTopLeft().getX() < 0
            || boundingBox.getBottomRight().getX() >= boardWidth
            || boundingBox.getTopLeft().getY() < 0
            || boundingBox.getBottomRight().getY() >= boardHeight;
    }
}
