package lighthouse.model;

import lighthouse.util.IntVec;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private BrickBuilder brickInProgress;
	private IntVec startPos;
	
	public void beginEdit(IntVec start) {
		brickInProgress = new BrickBuilder(start);
	}
	
	public void appendToEdit(Direction direction) {
		brickInProgress.append(direction);
	}
	
	public Brick finishEdit(IntVec end) {
		Brick brick = brickInProgress.build();
		
		startPos = null;
		brickInProgress = null;
		
		return brick;
	}
	
	public IntVec getStartPos() { return startPos; }
	
	public BrickBuilder getBrickInProgress() { return brickInProgress; }
}
