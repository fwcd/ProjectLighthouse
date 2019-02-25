package lighthouse.ui.board.view;

import java.awt.Color;

import lighthouse.model.Board;

/**
 * A class that wraps the board preparing it for
 * the big screen (the Lighthouse) by scaling and
 * translating the grid positions.
 */
public class LighthouseGrid {
	private final Board board;
	
	public LighthouseGrid(Board board) {
		this.board = board;
	}
	
	public Color getCell(int x, int y) {
		// TODO: Scaling/translating the input
		return board.getCell(x, y);
	}
}
