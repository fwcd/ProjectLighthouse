package lighthouse.puzzle.ui.board.controller;

import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.scene.controller.SceneResponder;

public interface BoardResponder extends SceneResponder {
	void updateViewModel(BoardViewModel viewModel);
}
