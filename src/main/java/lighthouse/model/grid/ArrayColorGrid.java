package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

/**
 * A simple, 2D-array-based implementation of {@link WritableColorGrid}.
 */
public class ArrayColorGrid implements WritableColorGrid {
	private final Color[][] colors;
	
	public ArrayColorGrid(int height, int width) {
		colors = new Color[height][width];
	}
	
	@Override
	public void setColorAt(int x, int y, Color color) {
		colors[y][x] = color;
	}
	
	@Override
	public Color getColorAt(IntVec pos) {
		return colors[pos.getY()][pos.getX()];
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return colors[y][x];
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
