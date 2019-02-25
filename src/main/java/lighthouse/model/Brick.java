package lighthouse.model;

import java.util.*;

public class Brick {

	public ArrayList<Direction> structure;

	public ArrayList<Edge> edges = new ArrayList<Edge>();
	{
		edges.add(new Edge(0, 0, Direction.UP));
		edges.add(new Edge(0, 0, Direction.RIGHT));
		edges.add(new Edge(0, 0, Direction.DOWN));
		edges.add(new Edge(0, 0, Direction.LEFT));
	}

	Direction rotation;
	public int xPos;
	public int yPos;

	public Brick(int x, int y, ArrayList<Direction> structList) {

		this.xPos = x;
		this.yPos = y;
		this.rotation = Direction.UP;
		this.structure = structList;
		int xOff = 0;
		int yOff = 0;
		for (Direction dir : structList) {
			xOff += dir.getDx();
			yOff += dir.getDy();
			int txOff = xOff;
			int tyOff = yOff;
			for (Direction inDir : Direction.values()) {
				if (!this.edges.stream()
						.anyMatch(edge -> edge.xOff == txOff - inDir.getDx() && edge.yOff == tyOff - inDir.getDy() && edge.dir == inDir)) {
					this.edges.add(new Edge(xOff, yOff, inDir.getOpposite()));
				} else {
					this.edges.removeIf(edge -> edge.xOff == txOff - inDir.getDx() && edge.yOff == tyOff - inDir.getDy() && edge.dir == inDir);
				}
			}
			
		}

	}
}
