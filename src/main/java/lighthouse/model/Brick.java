package lighthouse.model;

public class Brick{
	
	String structurure;
	Direction rotation;
	int xPos;
	int yPos;
	
	public Brick(int x, int y, String structList){
		this.xPos = x;
		this.yPos = y;
		this.rotation = Direction.UP;
		
	}
	
}
