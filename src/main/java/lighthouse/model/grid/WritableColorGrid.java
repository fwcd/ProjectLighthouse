package lighthouse.model.grid;

import java.awt.Color;

import lighthouse.util.IntVec;

public interface WritableColorGrid extends ColorGrid {
	void setColorAt(IntVec pos, Color color);
	
	void clear();
}
