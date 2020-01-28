package lighthouse.spaceinvaders.model;

import lighthouse.util.IntVec;

public class Projectile {
    private final IntVec position;
    private final IntVec velocity;
    private boolean hitsAliens;
    
    public Projectile(IntVec position, IntVec velocity, boolean hitsAliens) {
        this.position = position;
        this.velocity = velocity;
        this.hitsAliens = hitsAliens;
    }
    
    public IntVec getPosition() { return position; }
    
    public IntVec getVelocity() { return velocity; }
    
    public boolean doesHitAliens() { return hitsAliens; }
    
    public Projectile fly() { return new Projectile(position.add(velocity), velocity, hitsAliens); }
    
    public boolean isOutOfBounds(int boardWidth, int boardHeight) {
        return position.getX() < 0 || position.getX() >= boardWidth || position.getY() < 0 || position.getY() >= boardHeight;
    }
}
