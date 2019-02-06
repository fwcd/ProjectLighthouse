package lighthouse.model;

public enum Direction {
	UP(0),
	RIGHT(1),
	DOWN(2),
	LEFT(3);
	
	private int index;
	
	private Direction(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void rotateLeft(){
		this.index = (this.index - 1) % 4; 
	}
	
	public void rotateRight(){
		this.index = (this.index + 1) % 4;
	}
}
