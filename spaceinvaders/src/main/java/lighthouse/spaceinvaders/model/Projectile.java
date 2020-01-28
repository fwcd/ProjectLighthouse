package lighthouse.spaceinvaders.model;

import lighthouse.util.DoubleVec;

public class Projectile {
    private final DoubleVec position;
    private final DoubleVec velocity;
    private boolean hitsAliens;
    
    public Projectile(DoubleVec position, DoubleVec velocity, boolean hitsAliens) {
        this.position = position;
        this.velocity = velocity;
        this.hitsAliens = hitsAliens;
    }
    
    public DoubleVec getPosition() { return position; }
    
    public DoubleVec getVelocity() { return velocity; }
    
    public boolean doesHitAliens() { return hitsAliens; }
    
    public Projectile fly() { return new Projectile(position.add(velocity), velocity, hitsAliens); }
    
    public boolean isOutOfBounds(int boardWidth, int boardHeight) {
        return position.getX() < 0 || position.getX() >= boardWidth || position.getY() < 0 || position.getY() >= boardHeight;
    }
}
