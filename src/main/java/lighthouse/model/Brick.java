package lighthouse.model;

import java.util.*;

public class Brick {

	public List<Direction> structure;

	public List<Edge> edges = new ArrayList<Edge>();
	{
		edges.add(new Edge(0, 0, Direction.UP));
		edges.add(new Edge(0, 0, Direction.RIGHT));
		edges.add(new Edge(0, 0, Direction.DOWN));
		edges.add(new Edge(0, 0, Direction.LEFT));
	}

	Direction rotation;
	public int xPos;
	public int yPos;

	public Brick(int x, int y, List<Direction> structList) {

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Brick)) return false; 
		Brick brick = (Brick) obj;
		if (brick.xPos != xPos) return false;
		if (brick.yPos != yPos) return false;
		if (brick.structure.size() != structure.size()) return false;
		for (int i = 0; i < structure.size(); i++){
			if (brick.structure.get(i) != structure.get(i)) return false;
		}
		return true;
	}
}
