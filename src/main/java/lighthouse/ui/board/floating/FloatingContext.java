package lighthouse.ui.board.floating;

import lighthouse.model.GameBlock;
import lighthouse.util.IntVec;

/**
 * Wraps a "floating" block for the sole purpose
 * of visually more appealing mouse drags.
 */
public class FloatingContext {
	private GameBlock block;
	private IntVec pixelPos;
	
	public void clear() {
		block = null;
		pixelPos = null;
	}
	
	public void setPixelPos(IntVec pixelPos) { this.pixelPos = pixelPos; }
	
	public IntVec getPixelPos() { return pixelPos; }
	
	public void setBlock(GameBlock block) { this.block = block; }
	
	public GameBlock getBlock() { return block; }
}
