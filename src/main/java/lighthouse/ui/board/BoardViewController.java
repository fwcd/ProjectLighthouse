package lighthouse.ui.board;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lighthouse.model.Board;
import lighthouse.model.Status;
import lighthouse.ui.GameLoop;
import lighthouse.ui.board.controller.BoardEditController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.DelegateResponder;
import lighthouse.ui.board.input.BoardInput;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.input.BoardXboxControllerInput;
import lighthouse.ui.board.view.BoardView;
import lighthouse.ui.board.view.LighthouseGridView;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.util.ColorUtils;

/**
 * Manages the different board views. It assembles the
 * necessary inputs and views, while still allowing the
 * user of this class to hook custom views.
 */
public class BoardViewController {
	private final JComponent component;
	private Board model;
	
	private final List<LighthouseGridView> lhGridViews = new ArrayList<>();
	private final List<BoardView> boardViews = new ArrayList<>();
	
	private final DelegateResponder responder;
	private final GameLoop loop;

	public BoardViewController(Board model) {
		this.model = model;
		
		responder = new DelegateResponder(new BoardPlayController(model));
		loop = new GameLoop(lhGridViews, boardViews, model);

		// Creates a local view and hooks up the Swing component
		CoordinateMapper coordinateMapper = new ScaleTransform(70, 70);
		LocalBoardView localView = new LocalBoardView(coordinateMapper);
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
		
		// Start the game loop
		loop.start();
		newGame();
	}
	
	public void updateModel(Board model) {
		this.model = model;
		loop.updateBoard(model);
		responder.updateBoard(model);
	}
	
	public void newGame() {
		model.getEditState().setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		responder.setDelegate(new BoardPlayController(model));
	}
	
	public void edit() {
		model.getEditState().setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		responder.setDelegate(new BoardEditController(model));
	}
	
	public void reset() {
		responder.reset();
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
	
	public JComponent getLocalComponent() {
		return component;
	}
}
