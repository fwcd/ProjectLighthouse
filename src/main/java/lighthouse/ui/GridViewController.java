package lighthouse.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

/**
 * Manages the grid UI both locally and on the Lighthouse.
 * It assembles the necessary inputs and views.
 */
public class GridViewController {
	private static final Logger LOG = LoggerFactory.getLogger(GridViewController.class);
	private final JComponent component;
	private final List<GridView> views = new ArrayList<>();
	private final GridResponder responder;
	
	public GridViewController(Grid model) {
		responder = new GridController(model);
		
		// Creates a local view and hooks up the Swing component
		LocalGridView localView = new LocalGridView();
		component = localView.getComponent();
		views.add(localView);
		
		// Creates a remote Lighthouse view if the required login information is present
		ConfigFile auth = new ResourceConfigFile("/authentication.txt");
		if (auth.has("username") && auth.has("token")) {
			LighthouseGridView remoteView = new LighthouseGridView(auth.get("username"), auth.get("token"));
			views.add(remoteView);
		} else {
			LOG.warn("Warning: Authentication did not contain 'username' and/or 'token'");
		}
		
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
	}
	
	public void addView(GridView view) {
		views.add(view);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
