package lighthouse.ui.scene;

import java.util.ArrayList;
import java.util.List;

import lighthouse.ui.scene.view.SceneView;

public class SceneViewController {
	private final List<SceneView> views = new ArrayList<>();
	
	public SceneViewController() {
		
	}
	
	public void addView(SceneView view) {
		views.add(view);
	}
}
