package lighthouse.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameBoard;

/**
 * Provides a UI for the board.
 */
public class LocalGridView implements GridView {
	private final JComponent component;
	private final GameBoard model;
	private final List<GridViewResponder> delegates = new ArrayList<>();
	private int cellWidth = 10;
	
	public LocalGridView(GameBoard model) {
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
				responder.mouseDown(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				responder.mouseDrag(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				responder.mouseUp(e);
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
