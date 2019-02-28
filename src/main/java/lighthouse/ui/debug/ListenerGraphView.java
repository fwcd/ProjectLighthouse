package lighthouse.ui.debug;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.util.ColorUtils;
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (models != null) {
			Map<ListenerList<?>, IntVec> nodePositions = new HashMap<>();
			Map<ListenerList<?>, List<ListenerList<?>>> connections = new HashMap<>();
			int startX = 140;
			int rowDy = 80;
			IntVec pos = new IntVec(startX, 100);
			g2d.setFont(g2d.getFont().deriveFont(14.0F));
			
			for (ListenerGraph graph : models) {
				// Render nodes
				for (ListenerList<?> node : graph.getNodes()) {
					nodePositions.put(node, pos);
					IntVec size = renderNode(g2d, node, pos);
					pos = pos.add(size.getX() + 10, 0);
					if (pos.getX() >= (canvasSize.getWidth() - 80)) {
						pos = pos.withX(startX).add(0, rowDy);
					}
				}
				
				// Render edges
				for (GraphEdge edge : graph.getEdges()) {
					ListenerList<?> nodeA = graph.nodeByIndex(edge.getStart());
					ListenerList<?> nodeB = graph.nodeByIndex(edge.getEnd());
					
					connections.putIfAbsent(nodeA, new ArrayList<>());
					List<ListenerList<?>> outgoing = connections.get(nodeA);
					
					if (!outgoing.contains(nodeB)) {
						if (nodeA.wasFiredRecently(1000)) {
							g2d.setStroke(new BasicStroke(4.0F));
							g2d.setColor(Color.GREEN);
						} else {
							g2d.setStroke(new BasicStroke(2.0F));
							g2d.setColor(Color.DARK_GRAY);
						}
						outgoing.add(nodeB);
						IntVec posA = nodePositions.get(nodeA);
						IntVec posB = nodePositions.get(nodeB);
						g2d.drawLine(posA.getX(), posA.getY(), posB.getX(), posB.getY());
					}
				}
				
				pos = pos.withX(startX).add(0, rowDy);
			}
		}
	}
	
	private IntVec renderNode(Graphics2D g2d, ListenerList<?> node, IntVec pos) {
		FontMetrics metrics = g2d.getFontMetrics();
		String label = Integer.toHexString(node.hashCode()).substring(3) + ": " + node.getName();
		int strWidth = metrics.stringWidth(label);
		int strHeight = metrics.getHeight();
		int ovalWidth = strWidth + 20;
		int ovalHeight = strHeight + 20;
		int x = pos.getX() - (ovalWidth / 2);
		int y = pos.getY() - ovalHeight;
		
		g2d.setColor(node.wasFiredRecently(1000) ? ColorUtils.LIGHT_GREEN : Color.WHITE);
		g2d.fillOval(x, y, ovalWidth, ovalHeight);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(x, y, ovalWidth, ovalHeight);
		g2d.drawString(label, x + 4, y + strHeight + 4);
		
		return new IntVec(ovalWidth, ovalHeight);
	}
	
	public JComponent getComponent() { return component; }
}
