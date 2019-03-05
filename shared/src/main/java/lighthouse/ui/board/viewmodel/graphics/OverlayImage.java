package lighthouse.ui.board.viewmodel.graphics;

import java.awt.image.BufferedImage;

import lighthouse.util.DoubleVec;

public class OverlayImage implements SceneShape {
	private final DoubleVec topLeft;
	private final BufferedImage image;
	private final DoubleVec imageSize;
	
	public OverlayImage(DoubleVec topLeft, BufferedImage image) {
		this(topLeft, image, DoubleVec.ONE_ONE);
	}
	
	public OverlayImage(DoubleVec topLeft, BufferedImage image, DoubleVec imageSize) {
		this.topLeft = topLeft;
		this.image = image;
		this.imageSize = imageSize;
	}
	
	public DoubleVec getTopLeft() { return topLeft; }
	
	public BufferedImage getImage() { return image; }
	
	public DoubleVec getImageSize() { return imageSize; }
	
	@Override
	public void accept(SceneShapeVisitor visitor) {
		visitor.visitImage(this);
	}
}
