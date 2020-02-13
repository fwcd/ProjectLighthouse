package lighthouse.spaceinvaders.model;

import lighthouse.util.BoxBounded;
import lighthouse.util.DoubleRect;

public class Shield implements BoxBounded {
    private static final int MAX_HP = 4;
    private final DoubleRect boundingBox;
    private final HealthPoints hp;
    
    public Shield(DoubleRect boundingBox) {
        this(boundingBox, new HealthPoints(MAX_HP));
    }
    
    private Shield(DoubleRect boundingBox, HealthPoints hp) {
        this.boundingBox = boundingBox;
        this.hp = hp;
    }
    
    @Override
    public DoubleRect getBoundingBox() { return boundingBox; }

    public HealthPoints getHp() { return hp; }
    
    public Shield damage() { return new Shield(boundingBox, hp.decrease()); }
    
    public boolean isDestroyed() { return hp.areEmpty(); }
}
