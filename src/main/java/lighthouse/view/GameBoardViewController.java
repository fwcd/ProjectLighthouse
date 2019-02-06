package lighthouse.view;

import lighthouse.model.GameBoard;

public class GameBoardViewController {
	private final GameBoard model;
	private final GameBoardView view;
	
	public GameBoardViewController(GameBoard model) {
		this.model = model;
		view = new GameBoardView(model);
	}
	
	public GameBoardView getView() {
		return view;
	}
}
