package lighthouse.util;

@FunctionalInterface
public interface WithIntConsumer<T> {
	void accept(int i, T value);
}
