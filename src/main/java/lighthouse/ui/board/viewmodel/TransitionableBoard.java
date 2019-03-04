package lighthouse.ui.board.viewmodel;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.util.DoubleVec;

public class TransitionableBoard {
	private final Queue<Board> queuedBoards = new ArrayDeque<>();
	private final int framesPerTransition;
	private Board currentBoard;
	private BoardTransition activeTransition = null;
	private int frame = 0;
	
	public TransitionableBoard(Board currentBoard, int framesPerTransition) {
		this.currentBoard = currentBoard;
		this.framesPerTransition = framesPerTransition;
	}
	
	public void enqueueTransition(Board board) {
		queuedBoards.offer(board);
	}
	
	public boolean hasNextFrame() {
		return frame < (activeTransition.getTotalFrames() + 1);
	}
	
	public BoardTransition getActiveTransition() {
		return activeTransition;
	}
	
	public Optional<DoubleVec> gridPosForBrick(Brick brick) {
		if (activeTransition == null) {
			return Optional.empty();
		} else {
			return Optional.of(activeTransition.gridPosForBrick(brick, frame));
		}
	}
	
	public void nextFrame() {
		if (activeTransition != null) {
			frame++;
			if (frame >= activeTransition.getTotalFrames()) {
				nextTransition();
			}
		}
	}
	
	private void nextTransition() {
		frame = 0;
		if (queuedBoards.isEmpty()) {
			activeTransition = null;
		} else {
			Board nextBoard = queuedBoards.poll();
			activeTransition = new BoardTransition(currentBoard, nextBoard, framesPerTransition);
			currentBoard = nextBoard;
		}
	}
	
	public Board getCurrentBoard() {
		return currentBoard;
	}
}
