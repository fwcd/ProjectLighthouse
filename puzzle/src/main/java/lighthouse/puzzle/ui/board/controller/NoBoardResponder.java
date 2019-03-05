package lighthouse.puzzle.ui.board.controller;

import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

public class NoBoardResponder implements BoardResponder {
	public static final NoBoardResponder INSTANCE = new NoBoardResponder();
	
	private NoBoardResponder() {}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {}
}
