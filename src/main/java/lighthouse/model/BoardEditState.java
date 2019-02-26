package lighthouse.model;

import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private Status status;
	private BrickBuilder brickInProgress;
	private IntVec startPos;
	
	private final ListenerList<Status> statusListeners = new ListenerList<>();
	
	public void beginEdit(IntVec startPos) {
		brickInProgress = new BrickBuilder(startPos);
		this.startPos = startPos;
	}
	
	public void appendToEdit(Direction direction) {
		brickInProgress.append(direction);
	}
	
	public Brick finishEdit(IntVec end) {
		Brick brick = brickInProgress.build();
		reset();
		return brick;
	}
	
	public IntVec getStartPos() { return startPos; }
	
	public BrickBuilder getBrickInProgress() { return brickInProgress; }
	
	public Status getStatus() { return status; }
	
	public void reset() {
		brickInProgress = null;
		startPos = null;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<Status> getStatusListeners() { return statusListeners; }
}
