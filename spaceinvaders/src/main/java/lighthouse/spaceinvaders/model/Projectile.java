package lighthouse.spaceinvaders.model;

import lighthouse.util.IntVec;

public class Projectile {
    private final IntVec position;
    
    public Projectile(IntVec position) {
        this.position = position;
    }
    
    public IntVec getPosition() { return position; }
}
