package lighthouse.spaceinvaders.model;

import lighthouse.util.DoubleVec;

public class Alien {
    private final DoubleVec position;
    
    public Alien(DoubleVec position) {
        this.position = position;
    }
    
    public DoubleVec getPosition() { return position; }
    
    public Alien movedBy(DoubleVec delta) { return new Alien(position.add(delta)); }
    
    public boolean hitBy(Projectile projectile) { return projectile.doesHitAliens() && projectile.getPosition().equals(position); }
    
    public Projectile shoot() { return new Projectile(position, new DoubleVec(0, 1), false /* hitsAliens */); }
}
