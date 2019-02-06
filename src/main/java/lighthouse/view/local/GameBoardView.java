package lighthouse.view.local;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameBoard;

/**
 * Provides a UI for the board.
 */
public class GameBoardView {
	private final JComponent component;
	private final GameBoard model;
	
	public GameBoardView(GameBoard model) {
		this.model = model;
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
		
		GameBoardController controller = new GameBoardController(model);
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				controller.mouseDown(toGridX(e.getX()), toGridY(e.getY()));
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				controller.mouseDrag(toGridX(e.getX()), toGridY(e.getY()));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.mouseUp(toGridX(e.getX()), toGridY(e.getY()));
			}
		};
		component.addMouseListener(mouseAdapter);
		component.addMouseMotionListener(mouseAdapter);
	}
	
	/** Converts from a pixel coordinate to a grid column. */
	private int toGridX(int pixelX) {
		// TODO
		throw new RuntimeException();
	}
	
	/** Converts from a pixel coordinate to a grid row. */
	private int toGridY(int pixelY) {
		// TODO
		throw new RuntimeException();
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
		g2d.drawString("This is a board!", 30, 30);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
