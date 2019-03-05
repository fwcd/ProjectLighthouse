package lighthouse.puzzle.ui.board.viewmodel;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Brick;
import lighthouse.util.DoubleVec;

public class TransitionableBoard {
	private final Queue<Board> queuedBoards = new ArrayDeque<>();
	private final int framesPerTransition;
	private Board currentBoard;
	private Board visibleBoard;
	private BoardTransition activeTransition = null;
	private int frame = 0;
	
	public TransitionableBoard(Board currentBoard, int framesPerTransition) {
		this.currentBoard = currentBoard;
		this.framesPerTransition = framesPerTransition;
	}
	
	public void enqueueTransition(Board board) {
		currentBoard = board;
		
		if (visibleBoard == null) {
			visibleBoard = currentBoard;
		}
		
		queuedBoards.offer(board);
		
		if (activeTransition == null) {
			nextTransition();
		}
	}
	
	public boolean hasNextFrame() {
		return activeTransition != null && frame < (activeTransition.getTotalFrames() + 1);
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
			activeTransition = new BoardTransition(visibleBoard, nextBoard, framesPerTransition);
			visibleBoard = nextBoard;
		}
	}
	
	public Board getCurrentBoard() {
		return currentBoard;
	}
}
