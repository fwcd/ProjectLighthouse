package lighthouse.puzzle.ui.board;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.alee.extended.panel.CenterPanel;

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
	private final LocalBoardView view;
	
	public LocalBoardViewController(Board model, DoubleVecBijection gridToPixels) {
		responder = new DelegateResponder(NoResponder.INSTANCE);
		
		viewModel = new BoardViewModel(model);
		component = new JPanel(new BorderLayout());
		
		view = new LocalBoardView(gridToPixels);
		view.relayout(model.getColumns(), model.getRows());
		component.add(new CenterPanel(view.getComponent()), BorderLayout.CENTER);
		
		SceneMouseInput mouseInput = new SceneMouseInput(gridToPixels);
		mouseInput.addResponder(responder);
		view.addMouseInput(mouseInput);
		
		SceneKeyInput keyInput = new SceneKeyInput();
		keyInput.addResponder(responder);
		view.addKeyInput(keyInput);
	}
	
	public void render() {
		view.draw(viewModel);
		SwingUtilities.invokeLater(component::repaint);
	}
	
	public void setResponder(SceneResponder responder) { this.responder.setDelegate(responder); }
	
	public BoardViewModel getViewModel() { return viewModel; }
	
	@Override
	public JComponent getComponent() { return component; }
}
