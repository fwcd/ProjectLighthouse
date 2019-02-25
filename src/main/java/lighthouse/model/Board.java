package lighthouse.model;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import lighthouse.util.IntVec;

/**
 * The game board model representing
 * the entire state of the "Schimmler"-game.
 */
public class Board {
	private final int columns;
	private final int rows;
	private final Deque<Brick> bricks = new ArrayDeque<>();
	
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
		bricks.push(brick);
	}
	
	/** Fetches the cell's color at the specified position. */
	public Color colorAt(IntVec gridPos) {
		Brick brick = locateBrick(gridPos);
		if (brick == null) {
			return Color.BLACK;
		} else {
			int hash = Math.abs(brick.hashCode());
			return new Color(hash % 256, (hash % 120) * 2, (hash % 50) * 5);
		}
	}
	
	public Color colorAt(int x, int y) {
		return colorAt(new IntVec(x, y));
	}

	public Brick locateBrick(IntVec gridPos) {
		for (Brick brick: bricks) {
			IntVec start = brick.getPos();
			for (Direction dir : brick.getStructure()) {
				start = start.add(dir);
				if (start.equals(gridPos)) return brick;
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

	public Board copy() {
		Board copy = new Board(columns, rows);
		for (Brick brick : bricks) {
			copy.bricks.push(new Brick(brick.getPos(), brick.getStructure()));
		}
		return copy;
	}	
}
