package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

/**
 * A simple, 2D-array-based implementation of {@link WritableColorGrid}.
 */
public class ArrayColorGrid implements WritableColorGrid {
	private final Color[][] colors;
	private final int width;
	private final int height;
	private boolean outOfBoundsDrawingEnabled = true;
	
	public ArrayColorGrid(int height, int width) {
		this.height = height;
		this.width = width;
		colors = new Color[height][width];
	}
	
	@Override
	public void setColorAt(int x, int y, Color color) {
		if (!outOfBoundsDrawingEnabled || (x >= 0 && x < width && y >= 0 && y < height)) {
			colors[y][x] = color;
		}
	}
	
	@Override
	public Color getColorAt(IntVec pos) {
		return getColorAt(pos.getX(), pos.getY());
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		if (outOfBoundsDrawingEnabled && (x < 0 || x >= width || y < 0 || y >= height)) {
			return Color.BLACK;
		} else {
			return colors[y][x];
		}
	}
	
	public boolean isOutOfBoundsDrawingEnabled() {
		return outOfBoundsDrawingEnabled;
	}
	
	public void setOutOfBoundsDrawingEnabled(boolean outOfBoundsDrawingEnabled) {
		this.outOfBoundsDrawingEnabled = outOfBoundsDrawingEnabled;
	}
	
	@Override
	public void clear() {
		for (int y = 0; y < colors.length; y++) {
			for (int x = 0; x < colors[y].length; x++) {
				colors[y][x] = null;
			}
		}
	}
}
