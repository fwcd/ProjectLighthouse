package lighthouse.ui.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lighthouse.model.*;

/**
 * The primary responder implementation for playing.
 */
public class BoardPlayController implements BoardResponder {

	Board board;
	boolean dragEvent;
	Map<Direction, Integer> limits = new HashMap<Direction, Integer>();

	int startX;
	int startY;
	Brick brick;

	public BoardPlayController(Board model) {
		board = model;
	}

	void resetLimits(){
		limits.put(Direction.UP, 99);
		limits.put(Direction.DOWN, 99);
		limits.put(Direction.RIGHT, 99);
		limits.put(Direction.LEFT, 99);
	}
	
	@Override
	public void press(int gridX, int gridY) {
		brick = board.locateBlock(gridX, gridY);
		if (brick == null) return;
		dragEvent = true;
		startX = gridX;
		startY = gridY;
		List<Edge> edgeList = brick.edges;
		for (Direction dir : Direction.values()){
			edgeList.stream().filter(edge -> edge.dir.getIndex() == dir.getIndex()).forEach(edge -> {
				edge.highlighted = true;
				int xFace = brick.xPos + edge.xOff + dir.getDx();
				int yFace = brick.yPos + edge.yOff + dir.getDy();
				int limit = 0;
				while (board.locateBlock(xFace, yFace) == null && xFace < board.getColumns() && xFace >= 0 
				&& yFace < board.getRows() && yFace >= 0){
					limit += 1;
					xFace += dir.getDx();
					yFace += dir.getDy();
				}
				if (limits.get(dir) > limit) limits.put(dir, limit);
			});
		}

	}
	
	@Override
	public void dragTo(int gridX, int gridY) {
		if (!dragEvent) return;
		if (gridX != startX || gridY != startY){
			int atX = gridX - startX;
			int atY = gridY - startY;
			Direction atDir = Direction.getDirByOff(atX, atY);
			if (limits.get(atDir) > 0){
				limits.put(atDir,limits.get(atDir) -1);
				brick.xPos += atX;
				brick.yPos += atY;	
				startX = gridX;				
				startY = gridY;
			}
		}
	}
	
	@Override
	public void release(int gridX, int gridY) {
		if (dragEvent != true) return;
		resetLimits();
		for (Edge edge : brick.edges){
			edge.highlighted = false;
		}
		dragEvent = false;
	}

	
}
