package lighthouse.ui;

import lighthouse.ui.util.Status;
import lighthouse.util.ListenerList;

public class ObservableStatus {
	private Status status = null;
	private final ListenerList<Status> listeners = new ListenerList<>("GameContext.listeners");
	
	public Status get() { return status; }
	
	public void set(Status status) {
		this.status = status;
		listeners.fire(status);
	}
	
	public ListenerList<Status> getListeners() { return listeners; }
}
