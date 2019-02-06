package lighthouse.view;

import lighthouse.model.Board;

public class BoardViewController {
	private final Board model = new Board();
	private final BoardView view = new BoardView(model);
	
	public BoardView getView() {
		return view;
	}
}
