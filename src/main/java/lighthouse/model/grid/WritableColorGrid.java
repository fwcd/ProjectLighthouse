package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.ColorUtils;
import lighthouse.util.IntVec;

public interface WritableColorGrid extends ColorGrid {
	void setColorAt(int x, int y, Color color);
	
	/**
	 * Draws the color on top of the existing one.
	 * Only useful if the color is translucent.
	 */
	default void blendColorAt(int x, int y, Color color) {
		setColorAt(x, y, ColorUtils.blend(color, getColorAt(x, y)));
	}
	
	default void blendColorAt(IntVec pos, Color color) {
		blendColorAt(pos.getX(), pos.getY(), color);
	}
	
	default void setColorAt(IntVec pos, Color color) {
		setColorAt(pos.getX(), pos.getY(), color);
	}
	
	void clear();
	
	default void drawRect(int minX, int minY, int width, int height, Color color) {
		int maxX = minX + width;
		int maxY = minY + height;
		
		for (int y = minY; y < maxY; y++) {
			for (int x = minX; x < maxX; x++) {
				setColorAt(x, y, color);
			}
		}
	}
}
