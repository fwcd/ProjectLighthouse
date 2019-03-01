package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

public interface ColorGrid {
	Color getColorAt(IntVec gridPos);
	
	default Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
}
