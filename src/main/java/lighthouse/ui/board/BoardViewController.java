package lighthouse.ui.board;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lighthouse.model.Board;
import lighthouse.ui.GameLoop;
import lighthouse.ui.board.controller.BoardController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.input.BoardInput;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.input.BoardXboxControllerInput;
import lighthouse.ui.board.view.BoardView;
import lighthouse.ui.board.view.LocalBoardView;

/**
 * Manages the different board views. It assembles the
 * necessary inputs and views, while still allowing the
 * user of this class to hook custom views.
 */
public class BoardViewController {
	private final JComponent localComponent;
	private final List<BoardView> views = new ArrayList<>();
	private final BoardResponder responder;
	private final GameLoop loop;

	public BoardViewController(Board model) {
		responder = new BoardController(model);
		loop = new GameLoop(views, model);

		// Creates a local view and hooks up the Swing component
		LocalBoardView localView = new LocalBoardView();
		localComponent = localView.getComponent();
		addView(localView);
		
		// Adds mouse input
		BoardMouseInput mouseInput = new BoardMouseInput(localView.getCellWidth(), localView.getCellHeight());
		mouseInput.addResponder(responder);
		localView.addMouseListener(mouseInput);
		localView.addMouseMotionListener(mouseInput);
		
		// Adds keyboard input
		BoardKeyInput keyInput = new BoardKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyListener(keyInput);
		
		// Adds controller input
		BoardInput xboxInput = new BoardXboxControllerInput();
		xboxInput.addResponder(responder);
		
		// Start the game loop
		loop.start();
	}
	
	public void addView(BoardView view) {
		views.add(view);
	}
	
	public JComponent getLocalComponent() {
		return localComponent;
	}
}
