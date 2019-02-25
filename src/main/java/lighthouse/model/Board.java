package lighthouse.model;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The game board model representing
 * the entire state of the "Schimmler"-game.
 */
public class Board {
	private final int columns;
	private final int rows;
	private final Set<Brick> bricks = new HashSet<>();
	
	public Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}
	
	/** Fetches the board's column count. */
	public int getColumns() {
		return columns;
	}
	
	/** Fetches the board's row count. */
	public int getRows() {
		return rows;
	}
	
	/** Fetches all bricks on this board. */
	public Collection<Brick> getBricks() {
		return bricks;
	}
	
	public void add(Brick brick) {
		bricks.add(brick);
	}
	
	/** Fetches the cell's color at the specified position. */
	public Color getCell(int x, int y) {
		// TODO: Implement this correctly,
		// currently a random color is returned for debugging
		Random r = ThreadLocalRandom.current();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}

	public Brick locateBlock(int gridX, int gridY){
		for (Brick brick: bricks) {
			int startX = brick.xPos;
			int startY = brick.yPos;
			for (Direction dir : brick.structure){
				startX += dir.getDx();
				startY += dir.getDy();
				if (startX == gridX && startY == gridY) return brick;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Board)) return false; 
		Board board = (Board) obj;
		return board.bricks.equals(bricks);
	}

	public Board copy(){
		Board copy = new Board(columns, rows);
		for (Brick brick : bricks){
			copy.bricks.add(new Brick(brick.xPos, brick.yPos, brick.structure));
		}
		return copy;
	}	
}
