package lighthouse.util.transform;

import java.util.function.Function;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public interface DoubleVecBijection extends Bijection<DoubleVec, DoubleVec> {
	static DoubleVecBijection IDENTITY = new DoubleVecBijection() {
		@Override
		public DoubleVec apply(DoubleVec value) { return value; }
		
		@Override
		public DoubleVec inverseApply(DoubleVec value) { return value; }
	};
	
	default DoubleVecBijection inverse() {
		DoubleVecBijection outer = this;
		return new DoubleVecBijection() {
			@Override
			public DoubleVec apply(DoubleVec value) { return outer.inverseApply(value); }
			
			@Override
			public DoubleVec inverseApply(DoubleVec value) { return outer.apply(value); }
		};
	}
	
	default DoubleVec apply(IntVec intVec) {
		return apply(intVec.toDouble());
	}
	
	default DoubleVec inverseApply(IntVec intVec) {
		return inverseApply(intVec.toDouble());
	}
	
	default Function<DoubleVec, IntVec> floor() { return andThen(DoubleVec::floor); }
	
	default Function<DoubleVec, IntVec> ceil() { return andThen(DoubleVec::ceil); }
	
	default Function<DoubleVec, IntVec> round() { return andThen(DoubleVec::round); }
	
	default Function<DoubleVec, IntVec> castToInt() { return andThen(DoubleVec::castToInt); }
	
	default DoubleVecBijection compose(DoubleVecBijection inner) {
		DoubleVecBijection outer = this;
		return new DoubleVecBijection() {
			@Override
			public DoubleVec apply(DoubleVec value) { return outer.apply(inner.apply(value)); }
			
			@Override
			public DoubleVec inverseApply(DoubleVec value) { return inner.inverseApply(outer.inverseApply(value)); }
		};
	}
	
	default DoubleVecBijection andThen(DoubleVecBijection outer) {
		return outer.compose(this);
	}
}
