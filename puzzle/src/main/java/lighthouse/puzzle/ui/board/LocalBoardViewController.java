package lighthouse.puzzle.ui.board;

import javax.swing.JComponent;
import javax.swing.JPanel;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.ui.board.view.LocalBoardView;
import lighthouse.puzzle.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.controller.DelegateResponder;
import lighthouse.ui.scene.controller.NoResponder;
import lighthouse.ui.scene.controller.SceneResponder;
import lighthouse.ui.scene.input.SceneKeyInput;
import lighthouse.ui.scene.input.SceneMouseInput;
import lighthouse.util.transform.DoubleVecBijection;

public class LocalBoardViewController implements SwingViewController {
	private final JPanel component;
	private final BoardViewModel viewModel;
	private final DelegateResponder responder;
	
	public LocalBoardViewController(Board model, DoubleVecBijection gridToPixels) {
		responder = new DelegateResponder(NoResponder.INSTANCE);
		
		viewModel = new BoardViewModel(model);
		component = new JPanel();
		
		LocalBoardView view = new LocalBoardView(gridToPixels);
		
		SceneMouseInput mouseInput = new SceneMouseInput(gridToPixels);
		mouseInput.addResponder(responder);
		view.addMouseInput(mouseInput);
		
		SceneKeyInput keyInput = new SceneKeyInput();
		keyInput.addResponder(responder);
		view.addKeyInput(keyInput);
	}
	
	public void setResponder(SceneResponder responder) { this.responder.setDelegate(responder); }
	
	public BoardViewModel getViewModel() { return viewModel; }
	
	@Override
	public JComponent getComponent() { return component; }
}
