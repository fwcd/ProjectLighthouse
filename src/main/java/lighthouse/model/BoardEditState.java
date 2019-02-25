package lighthouse.model;

import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * The current editing state of the board. Any selections,
 * actively constructed objects will appear here. Not intended
 * to be serialized.
 */
public class BoardEditState {
	private String status;
	private BrickBuilder brickInProgress;
	private IntVec startPos;
	
	private final ListenerList<String> statusListeners = new ListenerList<>();
	
	public void beginEdit(IntVec startPos) {
		brickInProgress = new BrickBuilder(startPos);
		this.startPos = startPos;
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
	
	public String getStatus() { return status; }
	
	public void setStatus(String status) {
		this.status = status;
		statusListeners.fire(status);
	}
	
	public ListenerList<String> getStatusListeners() { return statusListeners; }
}
