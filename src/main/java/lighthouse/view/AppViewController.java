package lighthouse.view;

import lighthouse.model.AppModel;

/**
 * The application view logic.
 */
public class AppViewController {
	private final BoardViewController board = new BoardViewController();
	private final AppModel model = new AppModel();
	private final AppView view = new AppView(board.getView());
	
	public AppView getView() {
		return view;
	}
}
