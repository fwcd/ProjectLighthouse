package lighthouse.ui.grid;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lighthouse.model.Grid;
import lighthouse.ui.GameLoop;
import lighthouse.ui.grid.controller.GridController;
import lighthouse.ui.grid.controller.GridResponder;
import lighthouse.ui.grid.input.GridInput;
import lighthouse.ui.grid.input.GridKeyInput;
import lighthouse.ui.grid.input.GridMouseInput;
import lighthouse.ui.grid.input.GridXboxControllerInput;
import lighthouse.ui.grid.view.GridView;
import lighthouse.ui.grid.view.LocalGridView;

/**
 * Manages the grid UI both locally and on the Lighthouse. It assembles the
 * necessary inputs and views.
 */
public class GridViewController {
	private final JComponent component;
	private final List<GridView> views = new ArrayList<>();
	private final GridResponder responder;
	private final GameLoop loop;

	public GridViewController(Grid model) {
		responder = new GridController(model);
		loop = new GameLoop(views, model);

		// Creates a local view and hooks up the Swing component
		LocalGridView localView = new LocalGridView();
		component = localView.getComponent();
		addView(localView);
		
		// Adds mouse input
		GridMouseInput mouseInput = new GridMouseInput(localView.getCellWidth(), localView.getCellHeight());
		mouseInput.addResponder(responder);
		localView.addMouseListener(mouseInput);
		localView.addMouseMotionListener(mouseInput);
		
		// Adds keyboard input
		GridKeyInput keyInput = new GridKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyListener(keyInput);
		
		// Adds controller input
		GridInput xboxInput = new GridXboxControllerInput();
		xboxInput.addResponder(responder);
		
		// Start the game loop
		loop.start();
	}
	
	public void addView(GridView view) {
		views.add(view);
	}
	
	public JComponent getLocalComponent() {
		return component;
	}
}
