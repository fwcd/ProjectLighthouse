package lighthouse.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	
	@Override
	public void addResponder(GridViewResponder responder) {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				responder.mouseDown(toGridX(e.getX()), toGridY(e.getY()));
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				responder.mouseDrag(toGridX(e.getX()), toGridY(e.getY()));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				responder.mouseUp(toGridX(e.getX()), toGridY(e.getY()));
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
	}
	
	/** Converts a pixel x coordinate to a grid column. */
	private int toGridX(int pixelX) {
		return pixelX / cellWidth; // TODO: Deal with offsets
	}
	
	/** Converts a pixel y coordinate to a grid row. */
	private int toGridY(int pixelY) {
		return pixelY / cellWidth; // TODO: Deal with offsets
	}
	
	public JComponent getComponent() {
		return component;
	}
}
