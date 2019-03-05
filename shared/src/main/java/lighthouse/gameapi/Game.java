package lighthouse.gameapi;

import lighthouse.model.GameState;
import lighthouse.ui.ViewController;

public interface Game {
	String getName();
	
	GameState getModel();
	
	ViewController getCentralViewController();
	
	ViewController getControlsViewController();
}
