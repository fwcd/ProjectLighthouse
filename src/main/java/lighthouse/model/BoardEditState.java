package lighthouse.model;

import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private BrickBuilder brickInProgress;
	private final ListenerList<Void> changeListeners = new ListenerList<>("BoardEditState.changeListeners");
	
	public void beginEdit(Brick edited) {
		brickInProgress = new BrickBuilder(edited);
	}
	
	public void beginEdit(IntVec startPos) {
		brickInProgress = new BrickBuilder(startPos);
		changeListeners.fire();
	}
	
	public void appendToEdit(Direction direction) {
		brickInProgress.append(direction);
		changeListeners.fire();
	}
	
	public void moveBy(IntVec delta) {
		brickInProgress.moveBy(delta);
		changeListeners.fire();
	}
	
	public Brick finishEdit(IntVec end) {
		Brick brick = brickInProgress.build();
		reset();
		return brick;
	}
	
	public GameBlock getBrickInProgress() { return brickInProgress; }
	
	public ListenerList<Void> getChangeListeners() { return changeListeners; }
	
	public void reset() {
		brickInProgress = null;
		changeListeners.fire();
	}
}
