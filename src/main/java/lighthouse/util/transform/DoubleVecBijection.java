package lighthouse.util.transform;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public interface DoubleVecBijection extends Bijection<DoubleVec, DoubleVec> {
	default DoubleVec apply(IntVec intVec) {
		return apply(intVec.toDouble());
	}
	
	default DoubleVec inverse(IntVec intVec) {
		return inverse(intVec.toDouble());
	}
}
