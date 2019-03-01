package lighthouse.ui.board.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.ui.board.viewmodel.LighthouseViewModel;

/**
 * A local (Swing-based) view of the Lighthouse grid.
 */
public class LocalLighthouseView implements LighthouseView {
	private final JComponent component;
	private LighthouseViewModel viewModel = null;
	private Color gridLineColor = Color.DARK_GRAY;
	private boolean drawGrid = true;
	
	public LocalLighthouseView() {
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
	public void draw(LighthouseViewModel viewModel) {
		this.viewModel = viewModel;
		// Redraw the component
		SwingUtilities.invokeLater(component::repaint);
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (viewModel == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Board model drawn", 30, 30);
		} else {
			int cols = viewModel.getColumns();
			int rows = viewModel.getRows();
			
			int cellWidth = component.getPreferredSize().width / cols;
			int cellHeight = component.getPreferredSize().height / rows;
			
			// Draw the cell grid
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					g2d.setColor(viewModel.getColorAt(x, y));
					g2d.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
					if (drawGrid) {
						g2d.setColor(gridLineColor);
						g2d.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
					}
				}
			}
		}
	}
	
	public void addMouseListener(MouseListener listener) {
		component.addMouseListener(listener);
	}
	
	public void addMouseMotionListener(MouseMotionListener listener) {
		component.addMouseMotionListener(listener);
	}
	
	public void addKeyListener(KeyListener listener) {
		component.addKeyListener(listener);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
