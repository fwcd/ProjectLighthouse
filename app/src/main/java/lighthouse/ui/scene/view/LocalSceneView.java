package lighthouse.ui.scene.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import lighthouse.ui.scene.input.SceneKeyInput;
import lighthouse.ui.scene.input.SceneMouseInput;
import lighthouse.ui.scene.viewmodel.graphics.Graphics2DSceneRenderer;
import lighthouse.ui.scene.viewmodel.graphics.SceneShapeVisitor;
import lighthouse.ui.scene.viewmodel.graphics.SceneViewModel;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;

public class LocalSceneView implements SceneView {
	private final JComponent component;
	private SceneViewModel scene;
	
	private Function<DoubleVec, IntVec> gridPosToPixels;
	private Function<DoubleVec, IntVec> gridSizeToPixels;
	
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
		component.setBackground(Color.WHITE);
	}
	
	public void addKeyInput(SceneKeyInput keyInput) {
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();
		
		for (int keyCode : keyInput.getBoundKeys()) {
			inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), keyCode);
			actionMap.put(keyCode, new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					component.requestFocus();
					keyInput.keyPressed(keyCode);
				}
			});
		}
	}
	
	public void addMouseInput(SceneMouseInput mouseInput) {
		component.addMouseListener(mouseInput);
		component.addMouseMotionListener(mouseInput);
	}
	
	public void removeMouseInput(SceneMouseInput mouseInput) {
		component.removeMouseListener(mouseInput);
		component.removeMouseMotionListener(mouseInput);
	}
	
	public void setGridPosToPixels(Function<DoubleVec, IntVec> gridPosToPixels) {
		this.gridPosToPixels = gridPosToPixels;
	}
	
	public void setGridSizeToPixels(Function<DoubleVec, IntVec> gridSizeToPixels) {
		this.gridSizeToPixels = gridSizeToPixels;
	}
	
	@Override
	public void draw(SceneViewModel scene) {
		this.scene = scene;
		SwingUtilities.invokeLater(component::repaint);
	}
	
	public void relayout(IntVec gridSize) {
		IntVec mapped = gridSizeToPixels.apply(gridSize.toDouble());
		component.setPreferredSize(new Dimension(mapped.getX(), mapped.getY()));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (scene != null) {
			SceneShapeVisitor renderer = new Graphics2DSceneRenderer(g2d, gridPosToPixels, gridSizeToPixels);
			scene.acceptForAllLayers(renderer);
		}
	}
	
	public JComponent getComponent() { return component; }
}
