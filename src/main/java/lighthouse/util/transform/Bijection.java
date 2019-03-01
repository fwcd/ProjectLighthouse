package lighthouse.util.transform;

/**
 * Represents an invertible transformation.
 */
public interface Bijection<T> {
	T apply(T value);
	
	T inverse(T value);
	
	default Bijection<T> compose(Bijection<T> inner) {
		Bijection<T> outer = this;
		return new Bijection<T>() {
			@Override
			public T apply(T value) { return outer.apply(inner.apply(value)); }
			
			@Override
			public T inverse(T value) { return inner.inverse(outer.inverse(value)); }
		};
	}
	
	default Bijection<T> then(Bijection<T> outer) {
		return outer.compose(this);
	}
}
