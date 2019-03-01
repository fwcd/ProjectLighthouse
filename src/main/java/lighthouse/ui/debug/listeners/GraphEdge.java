package lighthouse.ui.debug.listeners;

/**
 * An edge in a directed graph.
 */
public class GraphEdge {
	private final int start;
	private final int end;
	
	public GraphEdge(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int getStart() { return start; }
	
	public int getEnd() { return end; }
}
