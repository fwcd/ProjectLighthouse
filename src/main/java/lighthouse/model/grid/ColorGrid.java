package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

public interface ColorGrid {
	Color getColorAt(int x, int y);
	
	default Color getColorAt(IntVec gridPos) {
		return getColorAt(gridPos.getX(), gridPos.getY());
	}
	
	default Color getColorOr(Color defaultColor, IntVec gridPos) {
		Color color = getColorAt(gridPos);
		return (color == null) ? defaultColor : color;
	}
	
	default Color getColorOr(Color defaultColor, int x, int y) {
		Color color = getColorAt(x, y);
		return (color == null) ? defaultColor : color;
	}
	
	default Color getColorOrBlackAt(IntVec gridPos) { return getColorOr(Color.BLACK, gridPos); }
	
	default Color getColorOrBlackAt(int x, int y) { return getColorOr(Color.BLACK, x, y); }
}
