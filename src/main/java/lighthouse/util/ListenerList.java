package lighthouse.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of listeners that accept values.
 */
public class ListenerList<T> implements Listener<T>, Iterable<Listener<T>> {
	private final List<Listener<T>> listeners = new ArrayList<>();
	
	private final String name;
	private long lastFiredTime = 0;
	
	public ListenerList() { this("?"); }
	
	public ListenerList(String name) { this.name = name; }
	
	public void add(Listener<T> listener) { listeners.add(listener); }
	
	public void remove(Listener<T> listener) { listeners.remove(listener); }
	
	public void fire(T value) {
		lastFiredTime = System.currentTimeMillis();
		for (Listener<T> listener : listeners) {
			listener.on(value);
		}
	}
	
	public void fire() { fire(null); }
	
	public long getLastFiredTime() { return lastFiredTime; }
	
	@Override
	public void on(T event) { fire(event); }
	
	@Override
	public String toString() { return name; }
	
	public String getName() { return name; }
	
	@Override
	public Iterator<Listener<T>> iterator() { return listeners.iterator(); }
}
