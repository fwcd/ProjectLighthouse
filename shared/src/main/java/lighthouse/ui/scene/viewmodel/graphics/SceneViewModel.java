package lighthouse.ui.scene.viewmodel.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SceneViewModel implements Iterable<SceneLayer> {
	private final List<SceneLayer> layers = new ArrayList<>();
	
	public void addLayer(SceneLayer layer) {
		layers.add(layer);
	}
	
	public void removeLayer(SceneLayer layer) {
		layers.remove(layer);
	}
	
	public void acceptForAllLayers(SceneShapeVisitor visitor) {
		for (SceneLayer layer : layers) {
			layer.acceptForAllShapes(visitor);
		}
	}
	
	public void setLayers(SceneLayer... layers) {
		this.layers.clear();
		for (SceneLayer layer : layers) {
			this.layers.add(layer);
		}
	}
	
	public int getLayerCount() {
		return layers.size();
	}
	
	@Override
	public Iterator<SceneLayer> iterator() {
		return layers.iterator();
	}
}
