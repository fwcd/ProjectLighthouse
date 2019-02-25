package lighthouse.model;

public class Edge{
	
	public int xOff;
	public int yOff;
	public Direction dir;
	boolean highlighted = false;
	
	public Edge(int x, int y, Direction dir){
		this.xOff = x;
		this.yOff = y;
		this.dir = dir;
	}	
	
}
