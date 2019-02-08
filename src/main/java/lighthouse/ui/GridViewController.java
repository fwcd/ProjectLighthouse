package lighthouse.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lighthouse.model.Grid;
import lighthouse.ui.controller.GridController;
import lighthouse.ui.controller.GridResponder;
import lighthouse.ui.input.GridInput;
import lighthouse.ui.input.GridKeyInput;
import lighthouse.ui.input.GridMouseInput;
import lighthouse.ui.input.GridXboxControllerInput;
import lighthouse.ui.view.GridView;
import lighthouse.ui.view.LighthouseGridView;
import lighthouse.ui.view.LocalGridView;

/**
 * Manages the grid UI.
 */
public class GridViewController {
	private final JComponent component;
	private final List<GridView> views = new ArrayList<>();
	private final GridResponder responder;
	
	public GridViewController(Grid model) {
		responder = new GridController(model);
		
		// Creates a local view and hooks up the Swing component
		LocalGridView localView = new LocalGridView(model);
		component = localView.getComponent();
		views.add(localView);
		
		// Creates a remote view
		LighthouseGridView remoteView = new LighthouseGridView(model);
		views.add(remoteView);
		
		// Adds mouse input
		GridMouseInput mouseInput = new GridMouseInput();
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
	}
	
	public void addView(GridView view) {
		views.add(view);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
