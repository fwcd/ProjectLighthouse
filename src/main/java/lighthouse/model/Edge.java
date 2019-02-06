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
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) return false;
		Edge ob = (Edge) obj;
		if (ob.xOff != this.xOff) return false;
		if (ob.yOff != this.yOff) return false;
		if (ob.dir != this.dir) return false;
		return true;
	}
	
	
	
}
