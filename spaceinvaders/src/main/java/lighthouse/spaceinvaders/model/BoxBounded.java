package lighthouse.spaceinvaders.model;

import lighthouse.util.DoubleRect;

public interface BoxBounded {
    DoubleRect getBoundingBox();
    
    default boolean collidesWith(BoxBounded rhs) {
        return getBoundingBox().intersects(rhs.getBoundingBox());
    }
}
