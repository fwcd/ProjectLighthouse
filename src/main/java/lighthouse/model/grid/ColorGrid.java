package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

public interface ColorGrid {
	Color getColorAt(int x, int y);
	
	default Color getColorAt(IntVec gridPos) {
		return getColorAt(gridPos.getX(), gridPos.getY());
	}
}
