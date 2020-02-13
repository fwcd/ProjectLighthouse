package lighthouse.spaceinvaders.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;

public class Shield implements BoxBounded {
    public static final int MAX_HP = 4;
    private final DoubleRect boundingBox;
    private final int hp;
    
    public Shield(DoubleRect boundingBox) {
        this(boundingBox, MAX_HP);
    }
    
    public Shield(DoubleRect boundingBox, int hp) {
        this.boundingBox = boundingBox;
        this.hp = hp;
    }
    
    @Override
    public DoubleRect getBoundingBox() { return boundingBox; }

    public int getHp() { return hp; }
    
    public Shield damage() { return new Shield(boundingBox, Math.max(0, hp - 1)); }
    
    public boolean isDestroyed() { return hp <= 0; }
}
