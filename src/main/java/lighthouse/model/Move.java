package lighthouse.model;

/**
 * A move that can be performed on a board.
 */
public class Move {
	private final Brick oldBrick;
	private final Brick newBrick;
	
	public Move(Brick oldBrick, Brick newBrick) {
		this.oldBrick = oldBrick;
		this.newBrick = newBrick;
	}
	
	public Brick getOldBrick() { return oldBrick; }
	
	public Brick getNewBrick() { return newBrick; }
	
	@Override
	public String toString() {
		return oldBrick + " -> " + newBrick;
	}
}
