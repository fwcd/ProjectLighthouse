package lighthouse.breakout.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lighthouse.util.Copyable;

public class Board implements Iterable<Brick>, Copyable<Board> {
	private final List<Brick> bricks = new ArrayList<>();
	
	public void addBrick(Brick brick) {
		bricks.add(brick);
	}
	
	public void removeBrick(Brick brick) {
		bricks.remove(brick);
	}
	
	@Override
	public Iterator<Brick> iterator() {
		return bricks.iterator();
	}
	
	@Override
	public Board copy() {
		Board copied = new Board();
		copied.bricks.addAll(bricks); // Bricks are immutable
		return copied;
	}
}
