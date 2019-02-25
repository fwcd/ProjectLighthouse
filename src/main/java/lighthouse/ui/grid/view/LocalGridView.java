package lighthouse.ui.grid.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.model.Grid;

/**
 * A local (Swing-based) view of the Lighthouse grid.
 */
public class LocalGridView implements GridView {
	private final JComponent component;
	private Grid model = null;
	private int cellWidth = 13;
	private int cellHeight = 30;
	
	public LocalGridView() {
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	/** Fetches the pixel width of each individual cell. */
	public int getCellWidth() {
		return cellWidth;
	}
	
	/** Fetches the pixel height of each individual cell. */
	public int getCellHeight() {
		return cellHeight;
	}
	
	@Override
	public void draw(Grid model) {
		this.model = model;
		// Redraw the component
		SwingUtilities.invokeLater(component::repaint);
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int cols = model.getColumns();
		int rows = model.getRows();
		
		cellWidth = Math.min(cellWidth, canvasSize.width / cols);
		cellHeight = Math.min(cellHeight, canvasSize.height / rows);
		
		if (model == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Grid model drawn", 30, 30);
		} else {
			// Draw the cell grid
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					g2d.setColor(model.getCell(x, y));
					g2d.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
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
