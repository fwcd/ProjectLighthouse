package lighthouse.spaceinvaders.model;

import lighthouse.util.DoubleRect;

public class Shield {
    private final DoubleRect boundingBox;
    private final int hp;
    
    public Shield(DoubleRect boundingBox, int hp) {
        this.boundingBox = boundingBox;
        this.hp = hp;
    }
    
    public DoubleRect getBoundingBox() { return boundingBox; }

    public int getHp() { return hp; }
    
    public boolean hitBy(Projectile projectile) { return boundingBox.contains(projectile.getPosition()); }
    
    public Shield damage() { return new Shield(boundingBox, Math.max(0, hp - 1)); }
}
