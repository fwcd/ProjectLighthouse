package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

public interface ColorGrid {
	Color getColorAt(int x, int y);
	
	default Color getColorAt(IntVec gridPos) {
		return getColorAt(gridPos.getX(), gridPos.getY());
	}
	
	default Color getColorOrBlackAt(IntVec gridPos) {
		Color color = getColorAt(gridPos);
		return (color == null) ? Color.BLACK : color;
	}
	
	default Color getColorOrBlackAt(int x, int y) {
		Color color = getColorAt(x, y);
		return (color == null) ? Color.BLACK : color;
	}
}
