package lighthouse.model;

import lighthouse.util.IntVec;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private BrickBuilder brickInProgress;
	
	public void newBrick(IntVec start) {
		brickInProgress = new BrickBuilder(start);
	}
	
	public BrickBuilder getBrickInProgress() {
		return brickInProgress;
	}
}
