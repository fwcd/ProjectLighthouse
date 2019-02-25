package lighthouse.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A list of listeners that accept values.
 */
public class ListenerList<T> {
	private final List<Consumer<T>> listeners = new ArrayList<>();
	
	public void add(Consumer<T> listener) {
		listeners.add(listener);
	}
	
	public void remove(Consumer<T> listener) {
		listeners.remove(listener);
	}
	
	public void fire(T value) {
		listeners.forEach(l -> l.accept(value));
	}
}
