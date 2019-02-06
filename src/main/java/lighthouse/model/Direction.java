package lighthouse.model;

public enum Direction {
	UP(0, 0, -1),
	RIGHT(1, 1, 0),
	DOWN(2, 0, 1),
	LEFT(3, -1, 0);
	
	private int index;
	private int dx;
	private int dy;
	
	private Direction(int index, int dx, int dy) {
		this.index = index;
		this.dx = dx;
		this.dy = dy;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getDX() {
		return dx;
	}
	public int getDY() {
		return dy;
	}
	
	public void rotateLeft(){
		this.index = (this.index - 1) % 4; 
	}
	
	public void rotateRight(){
		this.index = (this.index + 1) % 4;
	}
}
