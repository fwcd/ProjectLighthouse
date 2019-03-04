package lighthouse.ui.board.viewmodel;

import java.util.NoSuchElementException;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.util.DoubleVec;
import lighthouse.util.IntVec;
import lighthouse.util.interpolate.Interpolation;
import lighthouse.util.interpolate.LinearInterpolation;

public class BoardTransition {
	private final Board start;
	private final Board end;
	private final int totalFrames;
	private final Interpolation<IntVec, DoubleVec> interpolation = new LinearInterpolation();
	
	public BoardTransition(Board start, Board end, int totalFrames) {
		this.start = start;
		this.end = end;
		this.totalFrames = totalFrames;
	}
	
	public DoubleVec gridPosForBrick(Brick brick, int frame) {
		Brick startBrick = start.getBrickById(brick.getID()).orElse(null);
		Brick endBrick = end.getBrickById(brick.getID()).orElse(null);
		
		if (startBrick == null || endBrick == null) {
			return brick.getPos().toDouble();
		} else {
			return interpolation.interpolateBetween(startBrick.getPos(), endBrick.getPos(), frame / (double) totalFrames);
		}
	}
	
	public int getTotalFrames() { return totalFrames; }
	
	public Board getStart() { return start; }
	
	public Board getEnd() { return end; }
}
