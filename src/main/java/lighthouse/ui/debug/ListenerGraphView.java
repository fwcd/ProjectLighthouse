package lighthouse.ui.debug;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A graphical representation of the listener graph.
 */
public class ListenerGraphView {
	private final JComponent component;
	private ListenerGraph model;
	
	public ListenerGraphView() {
		component = new JPanel() {
			private static final long serialVersionUID = -6617076215160711298L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	public void draw(ListenerGraph model) {
		this.model = model;
		SwingUtilities.invokeLater(component::repaint);
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		// TODO
	}
	
	public JComponent getComponent() { return component; }
}
