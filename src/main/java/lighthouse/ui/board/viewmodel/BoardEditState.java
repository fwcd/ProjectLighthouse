package lighthouse.ui.board.viewmodel;

import lighthouse.model.Brick;
import lighthouse.model.BrickBuilder;
import lighthouse.model.Direction;
import lighthouse.model.GameBlock;
import lighthouse.util.IntVec;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private BrickBuilder brickInProgress;
	
	public void beginEdit(Brick edited) { brickInProgress = new BrickBuilder(edited); }
	
	public void beginEdit(IntVec startPos) { brickInProgress = new BrickBuilder(startPos); }
	
	public void appendToEdit(Direction direction) { brickInProgress.append(direction); }
	
	public void moveBy(IntVec delta) { brickInProgress.moveBy(delta); }
	
	public Brick finishEdit(IntVec end) {
		Brick brick = brickInProgress.build();
		reset();
		return brick;
	}
	
	public GameBlock getBrickInProgress() { return brickInProgress; }
	
	public void reset() { brickInProgress = null; }
}
