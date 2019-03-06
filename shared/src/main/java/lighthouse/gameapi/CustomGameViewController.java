package lighthouse.gameapi;

import lighthouse.ui.SwingViewController;
import lighthouse.ui.scene.input.SceneKeyInput;
import lighthouse.ui.scene.input.SceneMouseInput;

/**
 * A custom game view manager that additionally
 * provides the ability to hook into the scene's
 * input infrastructure and add a custom game view.
 */
public interface CustomGameViewController extends SwingViewController, RenderListener {
	void addMouseInput(SceneMouseInput mouseInput);
	
	void addKeyInput(SceneKeyInput keyInput);
	
	void removeMouseInput(SceneMouseInput mouseInput);
	
	void removeKeyInput(SceneKeyInput keyInput);
	
	Renderable getRenderableView();
}
