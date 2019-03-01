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
	
	default DoubleVecBijection compose(DoubleVecBijection inner) {
		DoubleVecBijection outer = this;
		return new DoubleVecBijection() {
			@Override
			public DoubleVec apply(DoubleVec value) { return outer.apply(inner.apply(value)); }
			
			@Override
			public DoubleVec inverse(DoubleVec value) { return inner.inverse(outer.inverse(value)); }
		};
	}
	
	default DoubleVecBijection andThen(DoubleVecBijection outer) {
		return outer.compose(this);
	}
}
