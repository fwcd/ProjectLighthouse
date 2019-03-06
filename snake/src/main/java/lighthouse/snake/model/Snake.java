package lighthouse.snake.model;

import java.util.Deque;
import java.util.ArrayDeque;
import lighthouse.util.IntVec;
import lighthouse.util.Direction;

public class Snake {
	private static final int START_LENGTH = 3;
	private final Deque<IntVec> body = new ArrayDeque<>();
	private final int boardWidth;
	private final int boardHeight;
	private Direction orientation = Direction.RIGHT;
	
	public Snake(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		
		for (int i = 0; i < START_LENGTH; i++) {
			IntVec pos = new IntVec(i, 0);
			body.offerFirst(pos);
		}
	}
	
	public void move(boolean shorten) {
		IntVec newHead = nextHead();
		body.offerFirst(newHead);
		if (shorten) {
			body.pollLast();
		}
	}
	
	public IntVec nextHead() {
		IntVec proposed = body.peekFirst().add(orientation);
		return new IntVec(
			Math.floorMod(proposed.getX(), boardWidth),
			Math.floorMod(proposed.getY(), boardHeight)
		);
	}
	
	public boolean contains(IntVec pos) {
		return body.contains(pos);
	}
	
	public Deque<IntVec> getBody() { return body; }
	
	public Direction getOrientation() { return orientation; }
}
