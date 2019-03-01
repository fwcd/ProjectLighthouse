package lighthouse.util.transform;

import lighthouse.util.IntVec;

/**
 * Represents an IntVec-translation.
 */
public class Translation implements Bijection<IntVec> {
	private int dx;
	private int dy;
	
	public Translation(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public IntVec apply(IntVec value) {
		return value.add(dx, dy);
	}
	
	@Override
	public IntVec inverse(IntVec value) {
		return value.sub(dx, dy);
	}
}
