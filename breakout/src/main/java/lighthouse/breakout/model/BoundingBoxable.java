package lighthouse.breakout.model;

import lighthouse.util.DoubleRect;

public interface BoundingBoxable {
	DoubleRect getBoundingBox();
	
	default boolean collidesWith(BoundingBoxable other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
}
