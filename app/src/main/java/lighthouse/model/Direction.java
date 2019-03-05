package lighthouse.model;

public enum Direction {
	UP(0, 0, -1, '^'),
	RIGHT(1, 1, 0, '>'),
	DOWN(2, 0, 1, 'v'),
	LEFT(3, -1, 0, '<');
	
	private final int index;
	private final int dx;
	private final int dy;
	private final char arrow;
	
	private Direction(int index, int dx, int dy, char arrow) {
		this.index = index;
		this.dx = dx;
		this.dy = dy;
		this.arrow = arrow;
	}
	
	public int getIndex() { return index; }
	
	public int getDx() { return dx; }
	
	public int getDy() { return dy; }
	
	public boolean isRightOrDown() { return this == RIGHT || this == DOWN; }
	
	public boolean isLeftOrRight() { return this == LEFT || this == RIGHT; }
	
	public Direction getOpposite() { return rotateLeft().rotateLeft(); }
	
	public Direction rotateLeft() { return values()[Math.floorMod(index - 1, 4)]; }
	
	public Direction rotateRight() { return values()[Math.floorMod(index + 1, 4)]; }
	
	public char toArrow() { return arrow; }
}
