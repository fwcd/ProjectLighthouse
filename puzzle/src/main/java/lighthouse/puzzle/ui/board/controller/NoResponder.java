package lighthouse.puzzle.ui.board.controller;

import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;

public class NoResponder implements BoardResponder {
	public static final NoResponder INSTANCE = new NoResponder();
	
	private NoResponder() {}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {}
}
