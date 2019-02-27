package lighthouse.ui.board.floating;

import lighthouse.model.Brick;
import lighthouse.util.IntVec;

/**
 * Wraps a "floating" brick for the sole purpose
 * of visually more appealing mouse drags.
 */
public class FloatingBrick {
	private Brick brick;
	private IntVec pixelPos;
	
	public void setPixelPos(IntVec pixelPos) { this.pixelPos = pixelPos; }
	
	public IntVec getPixelPos() { return pixelPos; }
	
	public void setBrick(Brick brick) { this.brick = brick; }
	
	public Brick getBrick() { return brick; }
}
