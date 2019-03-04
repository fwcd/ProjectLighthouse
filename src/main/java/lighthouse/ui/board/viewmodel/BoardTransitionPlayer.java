package lighthouse.ui.board.viewmodel;

import java.util.ArrayDeque;
import java.util.Queue;

import lighthouse.model.Board;

public class BoardTransitionPlayer {
	private final Queue<Board> queuedBoards = new ArrayDeque<>();
	private final int framesPerTransition;
	private Board currentBoard;
	private BoardTransition activeTransition = null;
	private int frame = 0;
	
	public BoardTransitionPlayer(Board currentBoard, int framesPerTransition) {
		this.currentBoard = currentBoard;
		this.framesPerTransition = framesPerTransition;
	}
	
	public void enqueueTransition(Board board) {
		queuedBoards.offer(board);
	}
	
	public boolean hasNextFrame() {
		return frame < (activeTransition.getTotalFrames() + 1);
	}
	
	public void nextFrame() {
		frame++;
		if (frame >= activeTransition.getTotalFrames()) {
			nextTransition();
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
}
