package lighthouse.ui.scene.view;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class LocalSceneView implements SceneView {
	private final JComponent component;
	
	public LocalSceneView() {
		component = new JPanel();
	}
	
	public JComponent getComponent() { return component; }
}
