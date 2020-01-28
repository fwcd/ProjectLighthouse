package lighthouse.spaceinvaders.model;

import lighthouse.util.IntVec;

public class Alien {
    private final IntVec position;
    
    public Alien(IntVec position) {
        this.position = position;
    }
    
    public IntVec getPosition() { return position; }
    
    public Alien movedBy(IntVec delta) { return new Alien(position.add(delta)); }
}
