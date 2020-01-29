package lighthouse.util;

public interface BoxBounded {
    DoubleRect getBoundingBox();
    
    default boolean collidesWith(BoxBounded rhs) {
        return getBoundingBox().intersects(rhs.getBoundingBox());
    }
}
