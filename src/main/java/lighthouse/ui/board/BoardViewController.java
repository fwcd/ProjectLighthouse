package lighthouse.ui.board;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lighthouse.model.Board;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.DelegateResponder;
import lighthouse.ui.board.floating.FloatingContext;
import lighthouse.ui.board.input.BoardInput;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.input.BoardXboxControllerInput;
import lighthouse.ui.board.view.BoardView;
import lighthouse.ui.board.view.LighthouseGrid;
import lighthouse.ui.board.view.LighthouseGridView;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.ui.loop.Renderer;

/**
 * Manages the different board views. It assembles the
 * necessary inputs and views, while still allowing the
 * user of this class to hook custom views.
 */
public class BoardViewController implements ViewController, Renderer {
	private final JComponent component;
	private Board model;
	private LighthouseGrid lhModel;
	
	private final List<LighthouseGridView> lhGridViews = new ArrayList<>();
	private final List<BoardView> boardViews = new ArrayList<>();
	
	private final FloatingContext floatingCtx = new FloatingContext();
	private final DelegateResponder responder;

	public BoardViewController(Board model) {
		responder = new DelegateResponder(new BoardPlayController(model, floatingCtx));
		updateModel(model);
		
		// Creates a local view and hooks up the Swing component
		CoordinateMapper coordinateMapper = new ScaleTransform(70, 70);
		LocalBoardView localView = new LocalBoardView(coordinateMapper, floatingCtx);
		localView.relayout(model.getColumns(), model.getRows());
		component = localView.getComponent();
		addBoardView(localView);
		
		// Adds mouse input
		BoardMouseInput mouseInput = new BoardMouseInput(coordinateMapper);
		mouseInput.addResponder(responder);
		localView.addMouseInput(mouseInput);
		
		// Adds keyboard input
		BoardKeyInput keyInput = new BoardKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);
		
		// Adds controller input
		BoardInput xboxInput = new BoardXboxControllerInput();
		xboxInput.addResponder(responder);
	}
	
	public void updateModel(Board model) {
		this.model = model;
		lhModel = new LighthouseGrid(model);
		responder.updateBoard(model);
	}
	
	public void setResponder(BoardResponder responder) {
		this.responder.setDelegate(responder);
	}
	
	public void reset() {
		responder.reset();
	}
	
	public FloatingContext getFloatingContext() { return floatingCtx; }

	@Override
	public void render() {
		for (BoardView view : boardViews) {
			view.draw(model);
		}
		for (LighthouseGridView lhView : lhGridViews) {
			lhView.draw(lhModel);
		}
	}
	
	public void addLighthouseGridView(LighthouseGridView view) {
		lhGridViews.add(view);
	}
	
	public void addBoardView(BoardView view) {
		boardViews.add(view);
	}
	
	public Board getModel() {
		return model;
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
