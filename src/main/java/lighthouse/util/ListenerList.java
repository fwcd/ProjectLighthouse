package lighthouse.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of listeners that accept values.
 */
public class ListenerList<T> implements Listener<T> {
	private final List<Listener<T>> listeners = new ArrayList<>();
	
	public void add(Listener<T> listener) {
		listeners.add(listener);
	}
	
	public void remove(Listener<T> listener) {
		listeners.remove(listener);
	}
	
	public void fire(T value) {
		for (Listener<T> listener : listeners) {
			listener.on(value);
		}
	}
	
	public void fire() {
		fire(null);
	}
	
	@Override
	public void on(T event) {
		fire(event);
	}
}
