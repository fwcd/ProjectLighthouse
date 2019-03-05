package lighthouse.util.interpolate;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class LogisticInterpolation implements Interpolation<IntVec, DoubleVec> {
	@Override
	public DoubleVec interpolateBetween(IntVec start, IntVec end, double percent) {
		IntVec connection = end.sub(start);
		return start.toDouble().add(connection.scale(sigmoid(percent * 6)));
	}
	
	private double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
}
