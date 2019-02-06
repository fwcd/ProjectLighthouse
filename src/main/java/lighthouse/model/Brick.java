package lighthouse.model;
import java.util.*;

public class Brick{
	
	ArrayList<Direction> structure;
	ArrayList<Edge> edges = new ArrayList<Edge>();
	{
		edges.add(new Edge(0, 0, Direction.UP));	
		edges.add(new Edge(0, 0, Direction.RIGHT));
		edges.add(new Edge(0, 0, Direction.DOWN));
		edges.add(new Edge(0, 0, Direction.LEFT));
	}
	
	Direction rotation;
	int xPos;
	int yPos;
	
	
	public Brick(int x, int y, ArrayList<Direction> structList){
		
		this.xPos = x;
		this.yPos = y;
		this.rotation = Direction.UP;
		this.structure = structList;
		int xOff = 0;
		int yOff = 0;
		for (Direction dir: structList){
			switch(dir){
				case UP:
					for (Direction inDir: Direction.values()){
						Edge test = new Edge(xOff, yOff, inDir);
						if(!this.edges.stream().anyMatch(edge -> edge.equals(test))){
							
						} 
					}
			}
		}	
	}
	
}
