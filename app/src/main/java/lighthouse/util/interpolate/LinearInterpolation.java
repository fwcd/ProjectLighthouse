package lighthouse.util.interpolate;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class LinearInterpolation implements Interpolation<IntVec, DoubleVec> {
	@Override
	public DoubleVec interpolateBetween(IntVec start, IntVec end, double percent) {
		IntVec connection = end.sub(start);
		return start.toDouble().add(connection.scale(percent));
	}
}
