package lighthouse.ui.board.controller;

import lighthouse.ui.board.viewmodel.BoardViewModel;

public class NoResponder implements BoardResponder {
	public static final NoResponder INSTANCE = new NoResponder();
	
	private NoResponder() {}
	
	@Override
	public void updateViewModel(BoardViewModel viewModel) {}
}
