package lighthouse.view;

import lighthouse.model.GameBoard;

public class GameBoardController {
	private final GameBoard model;
	private final GameBoardView view;
	
	public GameBoardController(GameBoard model) {
		this.model = model;
		view = new GameBoardView(model);
	}
	
	public GameBoardView getView() {
		return view;
	}
}
