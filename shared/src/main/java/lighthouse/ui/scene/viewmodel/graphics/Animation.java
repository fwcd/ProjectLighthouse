package lighthouse.ui.scene.viewmodel.graphics;

import java.util.List;

public interface Animation {
	String getName();
	
	int getTotalFrames();
	
	List<SceneShape> getShape(int frame);
	
	default int getLoopCount() { return 1; }
	
	default int getTotalLoopedFrames() { return getTotalFrames() * getLoopCount(); }
}
