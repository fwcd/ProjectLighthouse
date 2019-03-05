package lighthouse.puzzle.model;

import java.awt.Color;

import lighthouse.util.ColorUtils;

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
		Color oldColor = oldBrick.getColor();
		Color newColor = newBrick.getColor();
		if (oldColor.equals(newColor)) {
			return "(" + ColorUtils.describe(oldColor) + ") " + oldBrick.getPos() + " -> " + newBrick.getPos();
		} else {
			return ColorUtils.describe(oldColor) + ", " + oldBrick.getPos() + " -> " + ColorUtils.describe(oldColor) + ", " + newBrick.getPos();
		}
	}
}
