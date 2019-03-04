package lighthouse.ui.board.viewmodel.overlay;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;
import lighthouse.util.interpolate.Interpolation;
import lighthouse.util.interpolate.LinearInterpolation;

public class MovingImage implements Animation {
	private final BufferedImage image;
	private final int totalFrames = 300;
	private final IntVec start;
	private final IntVec end;
	private final Interpolation<IntVec, DoubleVec> interpolation = new LinearInterpolation();
	
	public MovingImage(BufferedImage image, IntVec start, IntVec end) {
		this.image = image;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int getTotalFrames() { return totalFrames; }
	
	@Override
	public String getName() { return "MovingImage"; }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		double percent = frame / (double) totalFrames;
		return Collections.singletonList(
			new OverlayImage(interpolation.interpolateBetween(start, end, percent), image)
		);
	}
}
