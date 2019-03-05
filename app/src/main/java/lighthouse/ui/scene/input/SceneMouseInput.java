package lighthouse.ui.scene.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.util.IntVec;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * A mouse-based grid input.
 */
public class SceneMouseInput extends MouseAdapter implements SceneInput {
	private final List<SceneResponder> responders = new ArrayList<>();
	private final DoubleVecBijection gridToPixels;
	
	public SceneMouseInput(DoubleVecBijection gridToPixels) {
		this.gridToPixels = gridToPixels;
	}
	
	@Override
	public void addResponder(SceneResponder responder) {
		responders.add(responder);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		IntVec pixelPos = pixelPosOf(e);
		IntVec gridPos = gridToPixels.inverseApply(pixelPos).floor();
		if (SwingUtilities.isRightMouseButton(e)) {
			responders.forEach(r -> r.rightPress(gridPos));
		} else {
			responders.forEach(r -> r.press(gridPos));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SwingUtilities.isRightMouseButton(e)) {
			IntVec pixelPos = pixelPosOf(e);
			IntVec gridPos = gridToPixels.inverseApply(pixelPos).floor();
			responders.forEach(r -> r.dragTo(gridPos));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isRightMouseButton(e)) {
			IntVec pixelPos = pixelPosOf(e);
			IntVec gridPos = gridToPixels.inverseApply(pixelPos).floor();
			responders.forEach(r -> r.release(gridPos));
		}
	}
	
	private IntVec pixelPosOf(MouseEvent e) {
		return new IntVec(e.getX(), e.getY());
	}
}
