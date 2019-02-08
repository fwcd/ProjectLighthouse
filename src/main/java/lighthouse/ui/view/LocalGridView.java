package lighthouse.ui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.Grid;

/**
 * Provides a UI for the board.
 */
public class LocalGridView implements GridView {
	private final JComponent component;
	private final Grid model;
	private int cellWidth = 10;
	
	public LocalGridView(Grid model) {
		this.model = model;
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	public int getCellWidth() {
		return cellWidth;
	}
	
	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
		g2d.drawString("This is a board!", 30, 30);
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
