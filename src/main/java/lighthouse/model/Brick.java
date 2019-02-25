package lighthouse.model;

import java.util.*;

/**
 * A block consisting of a list of directions.
 */
public class Brick {
	private final List<Direction> structure;
	private final List<Edge> edges = new ArrayList<>();
	
	private final Direction rotation;
	private int xPos;
	private int yPos;
	
	{
		edges.add(new Edge(0, 0, Direction.UP));
		edges.add(new Edge(0, 0, Direction.RIGHT));
		edges.add(new Edge(0, 0, Direction.DOWN));
		edges.add(new Edge(0, 0, Direction.LEFT));
	}
	
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
				if (!this.edges.stream().anyMatch(edge -> edge.matches(txOff, tyOff, dir))) {
					this.edges.add(new Edge(xOff, yOff, inDir.getOpposite()));
				} else {
					this.edges.removeIf(edge -> edge.matches(txOff, tyOff, dir));
				}
			}
		}
	}
	
	@Override
	public int hashCode() {
		return structure.hashCode() * edges.hashCode() * rotation.hashCode() * (xPos+1) * (yPos+1) * 7;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Brick)) return false; 
		Brick brick = (Brick) obj;
		if (brick.xPos != xPos) return false;
		if (brick.yPos != yPos) return false;
		return structure.equals(brick.structure);
	}
	
	public void moveBy(int dx, int dy) {
		xPos += dx;
		yPos += dy;
	}
	
	public int getXPos() { return xPos; }
	
	public int getYPos() { return yPos; }
	
	public List<Edge> getEdges() { return edges; }
	
	public List<Direction> getStructure() { return structure; }
}
