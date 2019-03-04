package lighthouse.util.interpolate;

public interface Interpolation<X, Y> {
	Y interpolateBetween(X start, X end, double percent);
}
