package lighthouse.util.transform;

import java.util.function.Function;

/**
 * Represents an invertible transformation.
 */
public interface Bijection<X, Y> extends Function<X, Y> {
	X inverse(Y value);
	
	default <A> Bijection<A, Y> compose(Bijection<A, X> inner) {
		Bijection<X, Y> outer = this;
		return new Bijection<A, Y>() {
			@Override
			public Y apply(A value) { return outer.apply(inner.apply(value)); }
			
			@Override
			public A inverse(Y value) { return inner.inverse(outer.inverse(value)); }
		};
	}
	
	default <B> Bijection<X, B> andThen(Bijection<Y, B> outer) {
		return outer.compose(this);
	}
}
