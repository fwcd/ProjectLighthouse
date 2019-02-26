package lighthouse.model;

import lighthouse.util.IntVec;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private BrickBuilder brickInProgress;
	
	public void beginEdit(Brick edited) {
		brickInProgress = new BrickBuilder(edited);
	}
	
	public void beginEdit(IntVec startPos) {
		brickInProgress = new BrickBuilder(startPos);
	}
	
	public void appendToEdit(Direction direction) {
		brickInProgress.append(direction);
	}
	
	public Brick finishEdit(IntVec end) {
		Brick brick = brickInProgress.build();
		reset();
		return brick;
	}
	
	public BrickBuilder getBrickInProgress() { return brickInProgress; }
	
	public void reset() {
		brickInProgress = null;
	}
}
