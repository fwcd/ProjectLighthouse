package lighthouse.spaceinvaders.model;

import lighthouse.util.IntVec;

public class Cannon {
    private final IntVec position;
    
    public Cannon(IntVec position) {
        this.position = position;
    }
    
    public Cannon movedBy(int dx) { return new Cannon(position.add(dx, 0)); }
    
    public Projectile shoot() { return new Projectile(position, new IntVec(0, -1), true /* hitsAliens */); }
}
