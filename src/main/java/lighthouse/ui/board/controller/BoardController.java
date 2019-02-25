package lighthouse.ui.board.controller;

import lighthouse.model.*;

/**
 * The primary responder implementation that
 * turns user inputs into changes to the model.
 */
public class BoardController implements BoardResponder {

	Board board;

	public BoardController(Board model) {
		board = model;
	}
	
	@Override
	public void press(int gridX, int gridY) {
		Brick brick = board.locateBlock(gridX, gridY);
	}
	
	@Override
	public void dragTo(int gridX, int gridY) {
		// TODO
	}
	
	@Override
	public void release(int gridX, int gridY) {
		// TODO
	}

	
}
