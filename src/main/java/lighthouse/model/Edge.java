package lighthouse.model;

public class Edge{
	
	int xOff;
	int yOff;
	Direction dir;
	
	public Edge(int x, int y, Direction dir){
		this.xOff = x;
		this.yOff = y;
		this.dir = dir;
	}
}
