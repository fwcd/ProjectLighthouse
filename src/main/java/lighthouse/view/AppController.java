package lighthouse.view;

import lighthouse.model.AppModel;

/**
 * The application UI logic.
 */
public class AppController {
	private final AppModel model = new AppModel();
	private final GameBoardController board = new GameBoardController(model.getBoard());
	private final AppView view = new AppView(board.getView());
	
	public AppView getView() {
		return view;
	}
}
