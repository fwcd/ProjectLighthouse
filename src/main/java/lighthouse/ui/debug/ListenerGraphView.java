package lighthouse.ui.debug;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * A graphical representation of the listener graph.
 */
public class ListenerGraphView {
	private final JComponent component;
	private List<ListenerGraph> models;
	
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
	
	public void draw(List<ListenerGraph> models) {
		this.models = models;
		SwingUtilities.invokeLater(component::repaint);
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (models != null) {
			int startX = 100;
			IntVec pos = new IntVec(startX, 100);
			g2d.setFont(g2d.getFont().deriveFont(14.0F));
			
			for (ListenerGraph graph : models) {
				// Render nodes
				for (ListenerList<?> node : graph.getNodes()) {
					int dx = renderNode(g2d, node.getName(), pos);
					pos = pos.add(dx, 0);
				}
				pos = pos.withX(startX).add(0, 50);
			}
		}
	}
	
	private int renderNode(Graphics2D g2d, String label, IntVec pos) {
		FontMetrics metrics = g2d.getFontMetrics();
		int strWidth = metrics.stringWidth(label);
		int strHeight = metrics.getHeight();
		int ovalWidth = strWidth + 20;
		int ovalHeight = strHeight + 20;
		int x = pos.getX() - (ovalWidth / 2);
		int y = pos.getY() - (ovalHeight / 2);
		
		g2d.setColor(Color.WHITE);
		g2d.fillOval(x, y, ovalWidth, ovalHeight);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(x, y, ovalWidth, ovalHeight);
		g2d.drawString(label, x + 4, y + strHeight + 4);
		
		return ovalWidth + 10;
	}
	
	public JComponent getComponent() { return component; }
}
