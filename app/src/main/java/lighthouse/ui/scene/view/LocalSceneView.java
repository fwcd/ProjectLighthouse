package lighthouse.ui.scene.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.ui.scene.viewmodel.graphics.Graphics2DSceneRenderer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneShapeVisitor;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class LocalSceneView implements SceneView {
	private final JComponent component;
	private SceneLayer scene;
	
	private final Function<DoubleVec, IntVec> gridPosToPixels;
	private final Function<DoubleVec, IntVec> gridSizeToPixels;
	
	public LocalSceneView(Function<DoubleVec, IntVec> gridToPixels) {
		this(gridToPixels, gridToPixels);
	}
	
	public LocalSceneView(Function<DoubleVec, IntVec> gridPosToPixels, Function<DoubleVec, IntVec> gridSizeToPixels) {
		this.gridPosToPixels = gridPosToPixels;
		this.gridSizeToPixels = gridSizeToPixels;
		
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	@Override
	public void draw(SceneLayer scene) {
		this.scene = scene;
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (scene != null) {
			SceneShapeVisitor renderer = new Graphics2DSceneRenderer(g2d, gridPosToPixels, gridSizeToPixels);
			scene.acceptForAllShapes(renderer);
		}
	}
	
	public JComponent getComponent() { return component; }
}
