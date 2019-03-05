package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;
import lighthouse.util.interpolate.Interpolation;
import lighthouse.util.interpolate.LinearInterpolation;

public class MovingImageAnimation implements Animation {
	private final BufferedImage image;
	private final int totalFrames = 300;
	private final IntVec start;
	private final IntVec end;
	private final DoubleVec size;
	private final Interpolation<IntVec, DoubleVec> interpolation = new LinearInterpolation();
	
	public MovingImageAnimation(BufferedImage image, IntVec start, IntVec end, DoubleVec size) {
		this.image = image;
		this.start = start;
		this.end = end;
		this.size = size;
	}
	
	@Override
	public int getTotalFrames() { return totalFrames; }
	
	@Override
	public String getName() { return "MovingImage"; }
	
	@Override
	public List<SceneShape> getShape(int frame) {
		double percent = frame / (double) totalFrames;
		return Collections.singletonList(
			new SceneImage(interpolation.interpolateBetween(start, end, percent), image, size)
		);
	}
}
