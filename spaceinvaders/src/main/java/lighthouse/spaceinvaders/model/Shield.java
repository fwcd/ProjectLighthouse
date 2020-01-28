package lighthouse.spaceinvaders.model;

import lighthouse.util.IntRect;

public class Shield {
    private final IntRect boundingBox;
    private final int hp;
    
    public Shield(IntRect boundingBox, int hp) {
        this.boundingBox = boundingBox;
        this.hp = hp;
    }
    
    public IntRect getBoundingBox() { return boundingBox; }

    public int getHp() { return hp; }
    
    public boolean hitBy(Projectile projectile) { return boundingBox.contains(projectile.getPosition()); }
    
    public Shield damage() { return new Shield(boundingBox, Math.max(0, hp - 1)); }
}
