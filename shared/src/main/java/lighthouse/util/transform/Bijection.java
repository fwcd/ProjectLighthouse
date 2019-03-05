package lighthouse.util.transform;

import java.util.function.Function;

/**
 * Represents an invertible transformation.
 */
public interface Bijection<X, Y> extends Function<X, Y> {
	X inverseApply(Y value);
	
	static <T> Bijection<T, T> identity() {
		return new Bijection<T, T>() {
			@Override
			public T apply(T value) { return value; }
			
			@Override
			public T inverseApply(T value) { return value; }
		};
	}
	
	default Bijection<Y, X> inverse() {
		Bijection<X, Y> outer = this;
		return new Bijection<Y, X>() {
			@Override
			public X apply(Y value) { return outer.inverseApply(value); }
			
			@Override
			public Y inverseApply(X value) { return outer.apply(value); }
		};
	}
	
	default <A> Bijection<A, Y> compose(Bijection<A, X> inner) {
		Bijection<X, Y> outer = this;
		return new Bijection<A, Y>() {
			@Override
			public Y apply(A value) { return outer.apply(inner.apply(value)); }
			
			@Override
			public A inverseApply(Y value) { return inner.inverseApply(outer.inverseApply(value)); }
		};
	}
	
	default <B> Bijection<X, B> andThen(Bijection<Y, B> outer) {
		return outer.compose(this);
	}
}
