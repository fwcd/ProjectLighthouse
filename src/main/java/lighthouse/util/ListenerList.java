package lighthouse.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of listeners that accept values.
 */
public class ListenerList<T> implements Listener<T> {
	private final List<Listener<T>> listeners = new ArrayList<>();
	private final String name;
	
	public ListenerList() { this("?"); }
	
	public ListenerList(String name) { this.name = name; }
	
	public void add(Listener<T> listener) { listeners.add(listener); }
	
	public void remove(Listener<T> listener) { listeners.remove(listener); }
	
	public void fire(T value) {
		for (Listener<T> listener : listeners) {
			listener.on(value);
		}
	}
	
	public void fire() { fire(null); }
	
	@Override
	public void on(T event) { fire(event); }
	
	@Override
	public String toString() { return name; }
	
	public String getName() { return name; }
}
