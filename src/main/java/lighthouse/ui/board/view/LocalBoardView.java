package lighthouse.ui.board.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.model.Board;

/**
 * A local high-resolution (Swing-based) view of the GameBoard.
 */
public class LocalBoardView implements BoardView {
	private final JComponent component;
	private Board model = null;
	private int cellWidth = 80;
	private int cellHeight = 80;
	
	public LocalBoardView() {
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
	public void draw(Board model) {
		this.model = model;
		// Redraw the component
		SwingUtilities.invokeLater(component::repaint);
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (model == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Board model drawn", 30, 30);
		} else {
			int cols = model.getColumns();
			int rows = model.getRows();
			
			cellWidth = Math.min(cellWidth, canvasSize.width / cols);
			cellHeight = Math.min(cellHeight, canvasSize.height / rows);
			
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
