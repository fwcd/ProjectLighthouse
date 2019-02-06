package lighthouse.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.model.GameBoard;

/**
 * Renders a board.
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
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
		g2d.drawString("This is a board!", 30, 30);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
