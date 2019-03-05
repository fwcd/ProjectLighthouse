package lighthouse.gameapi;

import lighthouse.model.GameState;
import lighthouse.ui.ViewController;

public interface Game {
	GameState getModel();
	
	ViewController getCentralViewController();
	
	ViewController getControlsViewController();
}
