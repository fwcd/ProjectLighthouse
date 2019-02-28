package lighthouse.ui.debug;

import java.util.ArrayList;
import java.util.List;

import lighthouse.util.Listener;
import lighthouse.util.ListenerList;

/**
 * A graph of listener lists, mainly intended
 * for debugging purposes.
 */
public class ListenerGraph {
	private final List<ListenerList<?>> nodes = new ArrayList<>();
	private final List<GraphEdge> edges = new ArrayList<>();
	
	public ListenerGraph(ListenerList<?>... roots) {
		for (ListenerList<?> root : roots) {
			buildWith(root);
		}
	}
	
	private int buildWith(ListenerList<?> node) {
		int existingIndex = nodes.indexOf(node);
		if (existingIndex >= 0) {
			// Handle cycles
			return existingIndex;
		} else {
			int index = nodes.size();
			nodes.add(node);
			for (Listener<?> child : node) {
				if (child instanceof ListenerList) {
					int endIndex = buildWith((ListenerList<?>) child);
					edges.add(new GraphEdge(index, endIndex));
				}
			}
			return index;
		}
	}
	
	public ListenerList<?> nodeByIndex(int index) { return nodes.get(index); }
	
	public List<ListenerList<?>> getNodes() { return nodes; }
	
	public List<GraphEdge> getEdges() { return edges; }
}
