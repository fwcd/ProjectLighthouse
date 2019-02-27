package lighthouse.ui.board.controller;

import lighthouse.model.Board;

public class NoResponder implements BoardResponder {
	public static final NoResponder INSTANCE = new NoResponder();
	
	private NoResponder() {}
	
	@Override
	public void updateBoard(Board board) {}
}
