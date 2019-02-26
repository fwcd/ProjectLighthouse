package lighthouse.util;

/**
 * A function that performs side-effects.
 */
@FunctionalInterface
public interface Listener<T> {
	void on(T event);
}
